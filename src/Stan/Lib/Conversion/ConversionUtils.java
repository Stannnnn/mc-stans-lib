package Stan.Lib.Conversion;

import org.bukkit.Location;

import Stan.Lib.World.Point;

public class ConversionUtils {

	public static Point toPoint(Location l){
		return new Point(l.getBlockX(), l.getBlockY(), l.getBlockZ());
	}
	
}
