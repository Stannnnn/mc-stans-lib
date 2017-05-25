package Stan.Lib.Actions;

import org.bukkit.entity.Player;

import Stan.Lib.Yml.Objects.Yml_Action;

public abstract class ActionRunnable implements Runnable, Cloneable {

	protected ActionExecutor actions;

	protected Yml_Action action;

	protected Player player;

	protected boolean failure;
	protected boolean waiting;
	
	public void preRun(){
		failure = false;
		waiting = false;
	}

	public abstract void run();
	
	public ActionRunnable clone() throws CloneNotSupportedException{
		return (ActionRunnable) super.clone();
	}

}
