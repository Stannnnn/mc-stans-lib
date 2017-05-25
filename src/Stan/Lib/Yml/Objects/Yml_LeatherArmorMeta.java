package Stan.Lib.Yml.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_LeatherArmorMeta extends Yml_ItemMeta {

	@YmlObject
	private Yml_Color color;

	@Override
	public ItemMeta getItemMeta(ItemStack itemStack, RW... replaceWrappers) {
		ItemMeta itemMeta = super.getItemMeta(itemStack, replaceWrappers);

		if (color != null) {
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
			leatherArmorMeta.setColor(color.getColor());
		}

		return itemMeta;
	}

}
