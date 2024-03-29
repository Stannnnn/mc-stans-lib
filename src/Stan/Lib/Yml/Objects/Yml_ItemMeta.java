package Stan.Lib.Yml.Objects;

import java.util.List;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.Yml.Annotations.YmlClass;
import Stan.Lib.Yml.Annotations.YmlObject;

@YmlClass(extensions = { Yml_LeatherArmorMeta.class, Yml_BannerMeta.class, Yml_SkullMeta.class })
public class Yml_ItemMeta {

	@YmlObject
	private String name;

	@YmlObject
	private List<String> lore;

	@YmlObject
	private List<Yml_Enchantment> enchantments;

	@YmlObject
	private List<String> flags;

	public ItemMeta getItemMeta(ItemStack itemStack, Object... replaceSources) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (name != null) {
			itemMeta.setDisplayName(ChatUtils.B(name, replaceSources));
		}

		if (lore != null) {
			itemMeta.setLore(ChatUtils.B(lore, replaceSources));
		}

		if (enchantments != null) {
			for (Yml_Enchantment enchant : enchantments) {
				itemMeta.addEnchant(enchant.getEnchantment(), enchant.getLevel(), enchant.getIgnoreRestrictions());
			}
		}

		if (flags != null) {
			for (String flag : flags) {
				itemMeta.addItemFlags(ItemFlag.valueOf(flag));
			}
		}

		return itemMeta;
	}

	@Override
	public String toString() {
		return "Yml_ItemMeta [name=" + name + ", lore=" + lore + ", enchantments=" + enchantments + ", flags=" + flags + "]";
	}

}
