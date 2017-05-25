//Version 1.2 (25-7-2016)
package Stan.Lib.Hooks;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import Stan.Lib.Main;

public class BaseHook implements Listener {
	protected final JavaPlugin currentPlugin;
	protected JavaPlugin hookedPlugin;

	private final String targetPluginName;

	public BaseHook(JavaPlugin currentPlugin, String targetPluginName) {
		this.targetPluginName = targetPluginName;
		this.currentPlugin = currentPlugin;
		
		Main.getInstance().getHookManager().registerHook(this);
	}	

	public boolean isHooked() {
		return hookedPlugin != null;
	}

	public void pluginHooked() {
	}

	public void pluginDetached() {
	}
	
	public String getTargetPluginName(){
		return targetPluginName;
	}
}