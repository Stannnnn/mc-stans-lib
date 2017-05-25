//Version 1.0 (29-7-2016)
package Stan.Lib.Yml.Objects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_ItemStack {

	@YmlObject
	private String id = "STONE";

	@YmlObject
	private int data = 0;

	@YmlObject
	private Yml_Amount amount;

	@YmlObject(skip = true)
	private Yml_ItemMeta itemMeta;

	public ItemStack getItemStack(Object... replaceSources) {
		ItemStack itemStack = new ItemStack(Material.getMaterial(id), amount.getAmount(), (short) data);
		itemStack.setItemMeta(itemMeta.getItemMeta(itemStack, replaceSources));
		return itemStack;
	}

	@Override
	public String toString() {
		return "Yml_ItemStack [id=" + id + ", data=" + data + ", amount=" + amount + ", itemMeta=" + itemMeta + "]";
	}

}
