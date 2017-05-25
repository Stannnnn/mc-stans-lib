package Stan.Lib.Inventories;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

	private final InventoryManager inventoryManager;

	public InventoryListener(InventoryManager inventoryManager) {
		this.inventoryManager = inventoryManager;
	}

	public void unregisterEvents() {
		InventoryCloseEvent.getHandlerList().unregister(this);
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		inventoryManager.removeInventory(e.getInventory());
	}

}
