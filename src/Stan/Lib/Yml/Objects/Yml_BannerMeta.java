package Stan.Lib.Yml.Objects;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import Stan.Lib.ReplaceWrappers.RW;
import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_BannerMeta extends Yml_ItemMeta {

	@YmlObject
	private Yml_Color color;

	@YmlObject
	private List<Yml_Pattern> patterns;

	@Override
	public ItemMeta getItemMeta(ItemStack itemStack, RW... replaceWrappers) {
		ItemMeta itemMeta = super.getItemMeta(itemStack, replaceWrappers);

		BannerMeta bannerMeta = (BannerMeta) itemMeta;

		if (color != null) {
			bannerMeta.setBaseColor(DyeColor.getByColor(color.getColor()));
		}

		if (patterns != null) {
			for (Yml_Pattern pattern : patterns) {
				bannerMeta.addPattern(pattern.getPattern());
			}
		}

		return itemMeta;
	}

}
