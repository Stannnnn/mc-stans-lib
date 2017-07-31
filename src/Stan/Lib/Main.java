package Stan.Lib;

import org.bukkit.plugin.java.JavaPlugin;

import Stan.Lib.Actions.ActionManager;
import Stan.Lib.Hooks.HoloHook;
import Stan.Lib.Hooks.HookManager;
import Stan.Lib.Hooks.PexHook;
import Stan.Lib.Hooks.VaultHook;
import Stan.Lib.Inventories.InventoryManager;
import Stan.Lib.Language.LanguageManager;
import Stan.Lib.Utils.AnsiUtils;
import Stan.Tests.MainTest;

public class Main extends JavaPlugin {

	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}

	private HookManager hookManager;
	private InventoryManager inventoryManager;
	private ActionManager actionManager;
	private LanguageManager languageManager;

	private VaultHook vaultHook;
	private PexHook pexHook;
	private HoloHook holoHook;

	@SuppressWarnings("unused")
	@Override
	public void onEnable() {
		instance = this;
		
		if (false){
			MainTest.executeTests();
		}
		
		System.out.println(AnsiUtils.ANSI_BLUE + "Loaded Stan's Lib" + Stan.Lib.Utils.AnsiUtils.ANSI_RESET);
	}

	@Override
	public void onDisable() {
		if (inventoryManager != null) {
			inventoryManager.onDisable();
		}

		if (actionManager != null) {
			actionManager.onDisable();
		}

		if (languageManager != null) {
			languageManager.onDisable();
		}
		
		if (hookManager != null){
			hookManager.onDisable();
		}
		
		System.out.println(AnsiUtils.ANSI_BLUE + "Unloaded Stan's Lib" + Stan.Lib.Utils.AnsiUtils.ANSI_RESET);
	}
	
	// Use this method to print failures related to the configuration of the plugin
	public void printFailure(String message) {
		System.out.println(AnsiUtils.ANSI_RED + "!!" + AnsiUtils.ANSI_RESET + " " + message);
	}
	
	public HookManager getHookManager() {
		return hookManager == null ? hookManager = new HookManager() : hookManager;
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager == null ? inventoryManager = new InventoryManager() : inventoryManager;
	}

	public ActionManager getActionManager() {
		return actionManager == null ? actionManager = new ActionManager() : actionManager;
	}

	public LanguageManager getLanguageManager() {
		return languageManager == null ? languageManager = new LanguageManager() : languageManager;
	}

	public VaultHook getVaultHook() {
		return vaultHook == null ? vaultHook = new VaultHook(this, "Vault") : vaultHook;
	}

	public PexHook getPexHook() {
		return pexHook == null ? pexHook = new PexHook(this, "PermissionsEx") : pexHook;
	}

	public HoloHook getHoloHook() {
		return holoHook == null ? holoHook = new HoloHook(this, "HolographicDisplays") : holoHook;
	}
}