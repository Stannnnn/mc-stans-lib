package Stan.Lib.Yml.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_LeatherArmorMeta extends Yml_ItemMeta {

	@YmlObject
	private Yml_Color color;

	@Override
	public ItemMeta getItemMeta(ItemStack itemStack, Object... replaceSources) {
		ItemMeta itemMeta = super.getItemMeta(itemStack, replaceSources);

		if (color != null) {
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
			leatherArmorMeta.setColor(color.getColor());
		}

		return itemMeta;
	}

}
