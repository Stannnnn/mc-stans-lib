package Stan.Lib.Hooks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import Stan.Lib.IManager;
import Stan.Lib.Main;

public class HookManager implements IManager, Listener {
	
	private Map<String, BaseHook> hooks;
	
	public HookManager(){
		hooks = new HashMap<>();
		Main.getInstance().getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}

	@Override
	public void onDisable() {
		List<BaseHook> hookz = new ArrayList<>();
		hookz.addAll(hooks.values());
		for (BaseHook baseHook : hookz){
			unregisterHook(baseHook);
		}
	}
	
	public boolean registerHook(BaseHook baseHook){
		if (!hooks.containsKey(baseHook.getTargetPluginName())){
			hooks.put(baseHook.getTargetPluginName(), baseHook);
			tryHook(baseHook);
			return true;
		}
		return false;
	}
	
	public boolean unregisterHook(BaseHook baseHook){
		if (hooks.containsKey(baseHook.getTargetPluginName())){
			hooks.remove(baseHook.getTargetPluginName(), baseHook);
			tryUnHook(baseHook);
			return true;
		}
		return false;
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginEnable(PluginEnableEvent e) {
		String targetPluginName = e.getPlugin().getName();
		if (e.getPlugin().isEnabled() && hooks.containsKey(targetPluginName)) {
			if (e.getPlugin() instanceof JavaPlugin) {
				BaseHook baseHook = hooks.get(targetPluginName);
				baseHook.hookedPlugin = (JavaPlugin) e.getPlugin();
				baseHook.pluginHooked();
			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPluginDisable(PluginDisableEvent e) {
		String targetPluginName = e.getPlugin().getName();
		if (!e.getPlugin().isEnabled() && hooks.containsKey(targetPluginName)) {
			if (e.getPlugin() instanceof JavaPlugin) {
				BaseHook baseHook = hooks.get(targetPluginName);
				baseHook.hookedPlugin = null;
				baseHook.pluginDetached();
			}
		}
	}
	
	private void tryHook(BaseHook baseHook) {
		if (!baseHook.isHooked()) {
			Plugin p = Main.getInstance().getServer().getPluginManager().getPlugin(baseHook.getTargetPluginName());
			if (p != null && p.isEnabled()) {
				if (p instanceof JavaPlugin) {
					baseHook.hookedPlugin = (JavaPlugin) p;
					baseHook.pluginHooked();
				}
			}
		}
	}
	
	private void tryUnHook(BaseHook baseHook){
		if (baseHook.isHooked()) {
			baseHook.hookedPlugin = null;
			baseHook.pluginDetached();
		}
	}

}
