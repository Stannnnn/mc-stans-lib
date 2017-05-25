package Stan.Lib.Hooks;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.line.HologramLine;
import com.gmail.filoghost.holographicdisplays.api.line.TextLine;

import Stan.Lib.Conversion.ChatUtils;

public class HoloHook extends BaseHook {

	public HoloHook(JavaPlugin currentPlugin, String targetPluginName) {
		super(currentPlugin, targetPluginName);
	}

	public boolean createHologram(Location loc, String name) {
		if (hookedPlugin == null) {
			return false;
		}

		Hologram hologram = HologramsAPI.createHologram(currentPlugin, loc);
		hologram.appendTextLine(ChatUtils.K(name));
		return true;
	}

	public boolean renameHologram(String oldName, String newName) {
		if (hookedPlugin == null) {
			return false;
		}

		oldName = ChatColor.stripColor(ChatUtils.K(oldName));

		Collection<Hologram> a = HologramsAPI.getHolograms(currentPlugin);
		for (Hologram b : a) {
			HologramLine c = b.getLine(0);
			if (c instanceof TextLine) {
				TextLine d = (TextLine) c;
				if (ChatColor.stripColor(d.getText()).equals(oldName)) {
					d.setText(ChatUtils.K(newName));
					return true;
				}
			}
		}

		return false;
	}

	public boolean refreshHologram(String name, Location loc) {
		if (hookedPlugin == null) {
			return false;
		}

		String rawName = ChatColor.stripColor(ChatUtils.K(name));

		Hologram e = null;

		Collection<Hologram> a = HologramsAPI.getHolograms(currentPlugin);
		for (Hologram b : a) {
			HologramLine c = b.getLine(0);
			if (c instanceof TextLine) {
				TextLine d = (TextLine) c;
				if (ChatColor.stripColor(d.getText()).equals(rawName)) {
					e = b;
				}
			}
		}

		if (e == null) {
			return createHologram(loc, name);
		}

		return false;
	}

	public boolean deleteHologram(String name) {
		if (hookedPlugin == null) {
			return false;
		}

		name = ChatColor.stripColor(ChatUtils.K(name));

		Collection<Hologram> a = HologramsAPI.getHolograms(currentPlugin);
		for (Hologram b : a) {
			HologramLine c = b.getLine(0);
			if (c instanceof TextLine) {
				TextLine d = (TextLine) c;
				if (ChatColor.stripColor(d.getText()).equals(name)) {
					b.delete();
					return true;
				}
			}
		}

		return false;
	}

	public boolean hasHologram(String name) {
		if (hookedPlugin == null) {
			return false;
		}

		name = ChatColor.stripColor(ChatUtils.K(name));

		Collection<Hologram> a = HologramsAPI.getHolograms(currentPlugin);
		for (Hologram b : a) {
			HologramLine c = b.getLine(0);
			if (c instanceof TextLine) {
				TextLine d = (TextLine) c;
				if (ChatColor.stripColor(d.getText()).equals(name)) {
					return true;
				}
			}
		}

		return false;
	}

}
