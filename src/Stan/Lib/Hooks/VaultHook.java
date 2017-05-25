package Stan.Lib.Hooks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import Stan.Lib.Main;
import net.milkbowl.vault.economy.Economy;

public class VaultHook extends BaseHook {

	private Economy econ;

	public VaultHook(JavaPlugin currentPlugin, String targetPluginName) {
		super(currentPlugin, targetPluginName);
	}

	@Override
	public void pluginHooked() {
		RegisteredServiceProvider<Economy> rsp = Main.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp != null) {
			econ = rsp.getProvider();
		}
	}

	@Override
	public void pluginDetached() {
		econ = null;
	}

	public boolean depositPlayer(Player p, double amount) {
		if (econ == null) {
			return false;
		}

		return econ.depositPlayer(p, amount).transactionSuccess();
	}

	public boolean hasBalance(Player p, double amount) {
		if (econ == null) {
			return false;
		}

		return econ.getBalance(p) >= amount;
	}

	public boolean withdrawPlayer(Player p, double amount) {
		if (econ == null) {
			return false;
		}

		return econ.withdrawPlayer(p, amount).transactionSuccess();
	}

}
