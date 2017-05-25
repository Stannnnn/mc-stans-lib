package Stan.Lib.Yml.Objects;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_SkullMeta extends Yml_ItemMeta {

	@YmlObject
	private String owner;

	@Override
	public ItemMeta getItemMeta(ItemStack itemStack, RW... replaceWrappers) {
		ItemMeta itemMeta = super.getItemMeta(itemStack, replaceWrappers);

		if (owner != null) {
			SkullMeta skullMeta = (SkullMeta) itemMeta;
			skullMeta.setOwner(ChatUtils.B(owner, replaceWrappers));
		}

		return itemMeta;
	}

}
