package Stan.Lib.Actions;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import Stan.Lib.Main;

public class ActionListener implements Listener {

	private final ActionManager actionManager;

	public ActionListener(ActionManager actionManager) {
		this.actionManager = actionManager;
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (actionManager.getAwaitingInput().containsKey(e.getPlayer().getName())) {
			e.setCancelled(true);
			e.getRecipients().clear();

			if (e.getMessage().equalsIgnoreCase("c") || e.getMessage().equalsIgnoreCase("cancel")) {
				actionManager.getAwaitingInput().get(e.getPlayer().getName()).cancel();
				Main.getInstance().getLanguageManager().sendLanguage(e.getPlayer(), "InputCancel");
			} else {
				actionManager.getAwaitingInput().get(e.getPlayer().getName()).result(e.getMessage());
			}
			
			actionManager.getAwaitingInput().remove(e.getPlayer().getName());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (actionManager.getAwaitingInput().containsKey(e.getPlayer().getName())) {
			actionManager.getAwaitingInput().get(e.getPlayer().getName()).cancel();
			actionManager.getAwaitingInput().remove(e.getPlayer().getName());
		}
	}

}
