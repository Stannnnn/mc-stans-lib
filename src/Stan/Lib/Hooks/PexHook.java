package Stan.Lib.Hooks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PexHook extends BaseHook {

	public PexHook(JavaPlugin currentPlugin, String targetPluginName) {
		super(currentPlugin, targetPluginName);
	}

	public boolean removeGroup(Player p, String permission) {
		if (hookedPlugin == null) {
			return false;
		}

		PermissionsEx.getUser(p).removeGroup(permission);
		return true;
	}

	public boolean addGroup(Player p, String permission) {
		if (hookedPlugin == null) {
			return false;
		}

		PermissionsEx.getUser(p).addGroup(permission);
		return true;
	}
	
	public boolean hasGroup(Player p, String groupName){
		if (hookedPlugin == null){
			return false;
		}
		
		return PermissionsEx.getUser(p).getParentIdentifiers().contains(groupName);
	}

	public boolean removePermission(Player p, String permission) {
		if (hookedPlugin == null) {
			return false;
		}

		PermissionsEx.getUser(p).removePermission(permission);
		return true;
	}

	public boolean addPermission(Player p, String permission) {
		if (hookedPlugin == null) {
			return false;
		}

		PermissionsEx.getUser(p).addPermission(permission);
		return true;
	}

}
