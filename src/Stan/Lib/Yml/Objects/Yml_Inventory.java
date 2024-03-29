package Stan.Lib.Yml.Objects;

import java.util.List;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import Stan.Lib.Main;
import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Inventory {

	@YmlObject
	private String name;

	@YmlObject
	private int size;

	@YmlObject
	private String type;

	@YmlObject
	private List<Yml_ItemStack> items;

	public Inventory getInventory(Object... replaceSources) {
		if (name != null) {
			if (size != 0) {
				return Main.getInstance().getInventoryManager().createInventory(size, ChatUtils.B(name, replaceSources));
			} else if (type != null) {
				return Main.getInstance().getInventoryManager().createInventory(InventoryType.valueOf(type), ChatUtils.B(name, replaceSources));
			}
		}

		return null;
	}
}
