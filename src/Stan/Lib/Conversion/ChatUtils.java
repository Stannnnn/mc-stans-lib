package Stan.Lib.Conversion;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import Stan.Lib.Main;
import Stan.Lib.ReplaceWrappers.RW;

/**
 * @author Stan
 * @version 1.0
 * @date 29-12-2015
 */
public class ChatUtils {
	
	public static String K(String s) {
		return s.contains("&") ? ChatColor.translateAlternateColorCodes('&', s) : s;
	}

	public static List<String> K(List<String> s) {
		List<String> tS = new ArrayList<>();
		for (String S : s) {
			tS.add(K(S));
		}
		return tS;
	}
	
	public static String R(String a, RW... replaceWrappers) {
		if (a != null && replaceWrappers != null) {
			for (RW rw : replaceWrappers) {
				if (rw != null && rw.getFrom() != null && a.contains(rw.getFrom().toString())) {
					a = a.replace(rw.getFrom().toString(), rw.getTo() == null ? "" : rw.getTo().toString());
				}
			}
		}

		return a;
	}

	public static List<String> R(List<String> a, RW... replaceWrappers) {
		List<String> tS = new ArrayList<>();
		for (String S : a) {
			tS.add(R(S, replaceWrappers));
		}
		return tS;
	}

	public static String B(String s, Object... replaceSources) {
		return K(R(s, Main.getInstance().getRWManager().getRWs(replaceSources)));
	}

	public static List<String> B(List<String> s, Object... replaceSources) {
		return K(R(s, Main.getInstance().getRWManager().getRWs(replaceSources)));
	}
	
	public static String S(String s){
		return ChatColor.stripColor(ChatUtils.K(s));
	}
	
	public static List<String> S(List<String> a) {
		List<String> tS = new ArrayList<>();
		for (String S : a) {
			tS.add(S(S));
		}
		return tS;
	}

}
