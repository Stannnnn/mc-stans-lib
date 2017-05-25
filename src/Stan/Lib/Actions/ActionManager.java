package Stan.Lib.Actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import Stan.Lib.IManager;
import Stan.Lib.Main;
import Stan.Lib.Conversion.CastUtils;
import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Utils.TitleAPI;
import Stan.Lib.Yml.YmlUtils;
import Stan.Lib.Yml.Objects.Yml_Action;
import Stan.Lib.Yml.Objects.Yml_Firework;
import Stan.Lib.Yml.Objects.Yml_Title;

public class ActionManager implements IManager {

	private Map<String, ActionExecutor> awaitingInput;
	private Map<String, ActionMethod> actionMethods;

	private ExecutorService executorService;

	public ActionManager() {
		awaitingInput = new HashMap<>();
		actionMethods = new HashMap<>();

		executorService = Executors.newCachedThreadPool();

		initializeDefaultActionMethods();

		Main.getInstance().getServer().getPluginManager().registerEvents(new ActionListener(this), Main.getInstance());
	}

	public Map<String, ActionExecutor> getAwaitingInput() {
		return awaitingInput;
	}

	public void executeActions(String fileName, YamlConfiguration yamlConfiguration, String configurationSection, RW... customVariables) {
		executeActions(fileName, yamlConfiguration, configurationSection, null, customVariables);
	}

	@SuppressWarnings("unchecked")
	public void executeActions(String fileName, YamlConfiguration yamlConfiguration, String configurationSection, Player player, RW... customVariables) {
		List<Yml_Action> actions = (List<Yml_Action>) YmlUtils.get(fileName, yamlConfiguration, configurationSection, Yml_Action.class, true);
		
		if (actions != null && !actions.isEmpty()) {
			executeActions(actions, player, customVariables);
		}
	}

	private void executeActions(List<Yml_Action> actions, Player player, RW... customVariables) {
		new ActionExecutor(actions).execute(executorService, player, customVariables);
	}

	@Override
	public void onDisable() {
		for (ActionExecutor a : awaitingInput.values()) {
			a.cancel();
		}

		executorService.shutdownNow();
		executorService = null;

		awaitingInput = null;
		actionMethods = null;
	}

	public ActionMethod getActionMethod(String actionMethod) {
		if (actionMethod == null) {
			return null;
		}

		if (!actionMethods.containsKey(actionMethod)) {
			return null;
		}

		return actionMethods.get(actionMethod);
	}

	public void addActionMethod(String name, ActionMethod actionMethod) {
		actionMethods.put(name, actionMethod);
	}

	public void removeActionMethod(String name) {
		actionMethods.remove(name);
	}

	private final void initializeDefaultActionMethods() {
		actionMethods.put("ConsoleCommand", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
			}
		}));

		actionMethods.put("ConsoleCommandX", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				String perm = action.getRequirements() != null ? action.getRequirements().getPermission() : null;

				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					if (perm == null || (perm != null && p.hasPermission(perm))) {
						Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), ChatUtils.B(action.getValue(), p, actions.getCustomVariables()));
					}
				}
			}
		}));

		actionMethods.put("PlayerCommand", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player != null) {
					player.performCommand(ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
				}
			}
		}));

		actionMethods.put("PlayerCommandX", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				String perm = action.getRequirements() != null ? action.getRequirements().getPermission() : null;

				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					if (perm == null || (perm != null && p.hasPermission(perm))) {
						p.performCommand(ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
					}
				}
			}
		}));

		actionMethods.put("ConsoleMessage", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				Main.getInstance().getServer().broadcastMessage(ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
			}
		}));

		actionMethods.put("PlayerMessage", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player != null) {
					System.out.println("NOWWW");
					
					for (RW rw : actions.getCustomVariables()){
						System.out.println(rw);
					}
					
					player.sendMessage(ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
				}
			}
		}));

		actionMethods.put("PlayerMessageX", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				String perm = action.getRequirements() != null ? action.getRequirements().getPermission() : null;

				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					if (perm == null || (perm != null && p.hasPermission(perm))) {
						p.sendMessage(ChatUtils.B(action.getValue(), player, actions.getCustomVariables()));
					}
				}
			}
		}));

		actionMethods.put("Sound", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player != null) {
					String soundName = ChatUtils.R(action.getValue(), new RW(".", "_")).toUpperCase();
					try {
						player.playSound(player.getLocation(), Sound.valueOf(soundName), 10.0F, 1.0F);
					} catch (Exception ex) {
						System.out.println("Soundname '" + soundName + "' not found");
					}
				}
			}
		}));

		actionMethods.put("SoundX", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				String perm = action.getRequirements() != null ? action.getRequirements().getPermission() : null;

				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					if (perm == null || (perm != null && p.hasPermission(perm))) {
						String soundName = ChatUtils.R(action.getValue(), new RW(".", "_")).toUpperCase();
						try {
							p.playSound(p.getLocation(), Sound.valueOf(soundName), 10.0F, 1.0F);
						} catch (Exception ex) {
							System.out.println("Soundname '" + soundName + "' not found");
						}
					}
				}
			}
		}));

		actionMethods.put("VaultPlayerWithdraw", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (!Main.getInstance().getVaultHook().isHooked()) {
					failure = true;
					return;
				}

				if (player == null) {
					failure = true;
					return;
				}

				if (CastUtils.isInt(action.getValue())) {
					int val = CastUtils.parseInt(action.getValue());

					if (!Main.getInstance().getVaultHook().withdrawPlayer(player, val)) {
						failure = true;
					}
				} else {
					failure = true;
				}
			}
		}));

		actionMethods.put("VaultPlayerDeposit", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (!Main.getInstance().getVaultHook().isHooked()) {
					failure = true;
					return;
				}

				if (player == null) {
					failure = true;
					return;
				}

				if (CastUtils.isInt(action.getValue())) {
					int val = CastUtils.parseInt(action.getValue());

					if (!Main.getInstance().getVaultHook().depositPlayer(player, val)) {
						failure = true;
					}
				} else {
					failure = true;
				}
			}
		}));

		actionMethods.put("VaultPlayerDeposit", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (!Main.getInstance().getVaultHook().isHooked()) {
					failure = true;
					return;
				}

				if (player == null) {
					failure = true;
					return;
				}

				if (CastUtils.isInt(action.getValue())) {
					int val = CastUtils.parseInt(action.getValue());

					if (!Main.getInstance().getVaultHook().hasBalance(player, val)) {
						failure = true;
					}
				} else {
					failure = true;
				}
			}
		}));

		actionMethods.put("AwaitInput", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player == null) {
					return;
				}

				InventoryView iv = player.getOpenInventory();
				if (iv != null) {
					actions.setOpenInventoryView(iv);
					iv.close();
				}

				Main.getInstance().getActionManager().getAwaitingInput().put(player.getName(), actions);

				waiting = true;

				while (waiting) {
					try {
						System.out.println(Thread.currentThread().hashCode());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		}, true));

		actionMethods.put("Title", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player == null) {
					return;
				}

				if (!(action.getActualValue() instanceof Yml_Title)) {
					return;
				}

				Yml_Title tit = (Yml_Title) action.getActualValue();

				TitleAPI.sendTitle(player, tit.getFadeIn(), tit.getStay(), tit.getFadeOut(), ChatUtils.B(tit.getTitle(), player, actions.getCustomVariables()), ChatUtils.B(tit.getSubtitle(), player, actions.getCustomVariables()));
			}
		}));

		actionMethods.put("Firework", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player == null) {
					return;
				}

				if (!(action.getActualValue() instanceof Yml_Firework)) {
					return;
				}

				Yml_Firework fw = (Yml_Firework) action.getActualValue();

				Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
				fw.getFirework(firework);
			}
		}));

		actionMethods.put("ReopenInventory", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				if (player == null) {
					return;
				}

				InventoryView iv = actions.getOpenInventoryView();
				if (iv != null) {
					player.openInventory(iv);
				}
			}
		}));

		actionMethods.put("Cancel", new ActionMethod(new ActionRunnable() {
			@Override
			public void run() {
				actions.cancel();
			}
		}, true));
	}

}