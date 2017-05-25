package Stan.Lib.Inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import Stan.Lib.IManager;
import Stan.Lib.Main;

public class InventoryManager implements IManager {

	private InventoryListener inventoryListener;
	private final List<Integer> activeInventories;

	private boolean registered;

	public InventoryManager() {
		activeInventories = new ArrayList<>();
	}

	@Override
	public void onDisable() {
		for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
			InventoryView inventory = p.getOpenInventory();
			if (inventory != null) {
				Inventory top = inventory.getTopInventory();
				Inventory bot = inventory.getBottomInventory();
				if ((top != null && activeInventories.contains(top.hashCode())) || (bot != null && activeInventories.contains(bot.hashCode()))) {
					inventory.close();
					activeInventories.remove(inventory.hashCode());
				}
			}
		}
	}

	public Inventory createInventory(InventoryType inventoryType, String name) {
		Inventory inventory = Main.getInstance().getServer().createInventory(null, inventoryType, name);
		activeInventories.add(inventory.hashCode());
		checkStatus();
		return inventory;
	}

	public Inventory createInventory(int size, String name) {
		Inventory inventory = Main.getInstance().getServer().createInventory(null, size, name);
		activeInventories.add(inventory.hashCode());
		checkStatus();
		return inventory;
	}

	public void removeInventory(Inventory inventory) {
		System.out.println("Checkin if we should remove the inventory." + (inventory.getViewers() != null ? "Size: " + inventory.getViewers().size() : ""));
		if (activeInventories.contains(inventory.hashCode())) {
			System.out.println("Removed inventory.");
			activeInventories.remove((Object) inventory.hashCode());
			checkStatus();
		}
	}

	private void checkStatus() {
		if (!registered && activeInventories.size() >= 1) {
			registered = true;

			if (inventoryListener == null) {
				inventoryListener = new InventoryListener(this);
			}

			Main.getInstance().getServer().getPluginManager().registerEvents(inventoryListener, Main.getInstance());
		}

		else if (registered && activeInventories.isEmpty()) {
			registered = false;

			if (inventoryListener != null) {
				inventoryListener.unregisterEvents();
			}
		}
	}

}
