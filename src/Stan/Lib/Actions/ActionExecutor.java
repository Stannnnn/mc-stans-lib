package Stan.Lib.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import Stan.Lib.Main;
import Stan.Lib.Conversion.StringUtils;
import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Yml.Objects.Yml_Action;

public class ActionExecutor {

	private final List<Yml_Action> actions;
	private ActionRunnable current;

	//private Thread thread;
	private boolean cont;

	private final List<String> results;
	private List<RW> customVariables;

	private InventoryView openInventoryView;

	public ActionExecutor(List<Yml_Action> actions) {
		this.actions = actions;
		this.cont = true;
		this.results = new ArrayList<>();
		this.customVariables = new ArrayList<>();
	}

	public void execute(final ExecutorService executorService, final Player player, final RW... replaceWrappers) {
		executorService.submit(new Runnable() {

			@Override
			public void run() {
				try {
					executeNow(actions, player, replaceWrappers);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	private void executeNow(List<Yml_Action> actions, Player player, RW... replaceWrappers) {
		for (Yml_Action a : actions) {
			if (!cont) {
				return;
			}
			
			if (!results.isEmpty()) {
				RW[] rw = new RW[results.size()];
				int i = 0;
				for (String result : results) {
					rw[i] = new RW("{Arg" + i + "}", result);
					i++;
				}

				replaceWrappers = RW.ConcatRW(rw);
			}
			
			List<RW> t = new ArrayList<>();
			t.addAll(customVariables);
			
			customVariables = new ArrayList<RW>();
			customVariables.addAll(Arrays.asList(RW.ConcatRW(t.toArray(new RW[t.size()]), replaceWrappers)));
			
			ActionMethod actionMethod = Main.getInstance().getActionManager().getActionMethod(a.getAction());
			if (actionMethod == null) {
				Main.getInstance().printFailure("Action " + a.getAction() + " does not exist.");
				continue;
			}
			
			try {
				actionMethod = actionMethod.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			
			actionMethod.initialize(this, a, player);
			current = actionMethod.getActionRunnable();
			actionMethod.execute();
			
			if (current.failure && a.getActionsOnFail() != null) {
				System.out.println("Hoi");
				
				//executeNow(a.getActionsOnFail(), player, replaceWrappers);
				
				final List<Yml_Action> za = a.getActionsOnFail();
				final Player b = player;
				final RW[] c = replaceWrappers;
							
				executeNow(za, b, c);
			}
		}
	}

	public void cancel() {
		cont = false;
		current.waiting = false;
	}

	public void result(String result) {
		results.add(result);
		current.waiting = false;
	}

	public Object getCustomVariable(String variable) {
		for (RW rw : customVariables) {
			if (rw.getFrom().equalsIgnoreCase(variable)) {
				return rw.getTo();
			}
		}

		return null;
	}

	public boolean hasCustomVariable(String variable) {
		for (RW rw : customVariables) {
			if (rw.getFrom().equalsIgnoreCase(variable)) {
				return true;
			}
		}

		return false;
	}

	public void removeCustomVariable(String variable) {
		List<RW> clone = new ArrayList<>();
		clone.addAll(customVariables);
		for (RW rw : clone) {
			if (rw.getFrom().equalsIgnoreCase(variable)) {
				customVariables.remove(rw.getFrom());
			}
		}
	}

	public void setCustomVariable(String variable, String value) {
		if (!Character.isUpperCase(variable.charAt(0))){
			variable = StringUtils.upperFirst(variable);
		}
		
		if (!variable.startsWith("{")){
			throw new IllegalArgumentException("Variables must start with {");
		}
		
		if (!variable.endsWith("}")){
			throw new IllegalArgumentException("Variables must end with }");
		}
		
		if (hasCustomVariable(variable)) {
			removeCustomVariable(variable);
		}

		customVariables.add(new RW(variable, value));
	}

	public RW[] getCustomVariables() {
		return customVariables.toArray(new RW[customVariables.size()]);
	}

	public InventoryView getOpenInventoryView() {
		return openInventoryView;
	}

	public void setOpenInventoryView(InventoryView openInventoryView) {
		this.openInventoryView = openInventoryView;
	}

}
