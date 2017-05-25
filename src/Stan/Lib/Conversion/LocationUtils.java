//Version 1.0 (29-7-2016)
package Stan.Lib.Conversion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class LocationUtils {
	public static Location StrToLoc(JavaPlugin javaPlugin, String sLocation) {
		if (sLocation != null && sLocation.contains(":")) {
			String args[] = sLocation.split(":");
			if (javaPlugin.getServer().getWorld(args[0]) != null) {
				if (args.length == 6) {
					if (javaPlugin.getServer().getWorld(args[0]) != null) {
						return new Location(javaPlugin.getServer().getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
					}
				} else if (args.length == 4) {
					if (javaPlugin.getServer().getWorld(args[0]) != null) {
						return new Location(javaPlugin.getServer().getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
					}
				}
			}
		}
		return null;
	}

	public static List<Location> ListStrToLoc(JavaPlugin javaPlugin, List<String> sLocations) {
		List<Location> nLocations = new ArrayList<>();
		for (String loc : sLocations) {
			nLocations.add(StrToLoc(javaPlugin, loc));
		}
		return nLocations;
	}

	public static String LocToStr(Location loc, boolean yawPitch) {
		if (loc != null && loc.getWorld() != null) {
			if (yawPitch) {
				return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ() + ":" + Math.round(loc.getYaw()) + ":" + Math.round(loc.getPitch());
			}
			return loc.getWorld().getName() + ":" + loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ();
		}
		return null;
	}

	public static List<String> ListLocToStr(List<Location> nLocations, boolean yawPitch) {
		List<String> sLocations = new ArrayList<>();
		for (Location loc : nLocations) {
			sLocations.add(LocToStr(loc, yawPitch));
		}
		return sLocations;
	}
}
