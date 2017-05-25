// Credits: ConnorLinfoot at https://www.spigotmc.org/resources/actionbarapi-1-8-1-9-1-10.1315/
package Stan.Lib.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import Stan.Lib.Main;

public class ActionBarAPI {

	public static void sendActionBar(Player player, String message) {
		try {
			String nmsver = Main.getInstance().getServer().getClass().getPackage().getName();
			nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

			Object ppoc;
			Class<?> c3;
			Class<?> c2;
			Class<?> c1 = Class.forName("org.bukkit.craftbukkit." + nmsver + ".entity.CraftPlayer");
			Object p = c1.cast((Object) player);
			Class<?> c4 = Class.forName("net.minecraft.server." + nmsver + ".PacketPlayOutChat");
			Class<?> c5 = Class.forName("net.minecraft.server." + nmsver + ".Packet");

			c2 = Class.forName("net.minecraft.server." + nmsver + ".ChatComponentText");
			c3 = Class.forName("net.minecraft.server." + nmsver + ".IChatBaseComponent");
			Object o = c2.getConstructor(String.class).newInstance(message);
			ppoc = c4.getConstructor(c3, Byte.TYPE).newInstance(o, Byte.valueOf((byte) 2));

			Method m1 = c1.getDeclaredMethod("getHandle", new Class[0]);
			Object h = m1.invoke(p, new Object[0]);
			Field f1 = h.getClass().getDeclaredField("playerConnection");
			Object pc = f1.get(h);
			Method m5 = pc.getClass().getDeclaredMethod("sendPacket", c5);
			m5.invoke(pc, ppoc);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void sendActionBar(final Player player, final String message, int duration) {
		ActionBarAPI.sendActionBar(player, message);
		if (duration >= 0) {
			Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(),
			new Runnable() {

				public void run() {
					clearActionBar(player);
				}
			},(long) (duration + 1));
		}
		while (duration > 60) {
			int sched = (duration -= 60) % 60;
			Main.getInstance().getServer().getScheduler().runTaskLater(Main.getInstance(), 
					new Runnable() {

				public void run() {
					ActionBarAPI.sendActionBar(player, message);
				}
			},(long) sched);
		}
	}

	public static void sendActionBarToAllPlayers(String message) {
		ActionBarAPI.sendActionBarToAllPlayers(message, -1);
	}

	public static void sendActionBarToAllPlayers(String message, int duration) {
		for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
			ActionBarAPI.sendActionBar(p, message, duration);
		}
	}
	
	public static void clearActionBar(Player player){
		ActionBarAPI.sendActionBar(player, "");
	}

}
