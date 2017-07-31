package Stan.Lib.Actions;

import org.bukkit.entity.Player;

import Stan.Lib.Main;
import Stan.Lib.Yml.Objects.Yml_Action;

public class ActionMethod implements Cloneable {

	private final boolean async;
	private ActionRunnable runnable;

	public ActionMethod(ActionRunnable runnable) {
		this(runnable, false);
	}

	public ActionMethod(ActionRunnable runnable, boolean async) {
		this.runnable = runnable;
		this.async = async;
	}
	
	public void initialize(ActionExecutor actions, Yml_Action action, Player player){
		runnable.actions = actions;
		runnable.action = action;
		runnable.player = player;
	}

	public ActionRunnable execute() {
		if (runnable.actions == null){
			throw new IllegalArgumentException("Call initialize before calling execute!");
		}
		
		if (async) {
			runnable.run();
		} else {
			Main.getInstance().getServer().getScheduler().runTask(Main.getInstance(), runnable);
		}
		
		return runnable;
	}
	
	public ActionRunnable getActionRunnable(){
		return runnable;
	}
	
	public ActionMethod clone() throws CloneNotSupportedException{
		ActionMethod am = (ActionMethod) super.clone();
		am.runnable = runnable.clone();
		return am;
	}

}
