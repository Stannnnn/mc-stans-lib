package Stan.Lib.Yml.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import Stan.Lib.Conversion.ChatUtils;
import Stan.Lib.ReplaceWrappers.RW;
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
	
	public Yml_ItemMeta(){
		
	}
	
	private Yml_ItemMeta(Builder builder){
		this.name = builder.name;
		this.lore = builder.lore;
		this.enchantments = builder.enchantments;
		this.flags = builder.flags;
	}

	public ItemMeta getItemMeta(ItemStack itemStack, RW... replaceWrappers) {
		ItemMeta itemMeta = itemStack.getItemMeta();

		if (name != null) {
			itemMeta.setDisplayName(ChatUtils.B(name, replaceWrappers));
		}

		if (lore != null) {
			itemMeta.setLore(ChatUtils.B(lore, replaceWrappers));
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

	public static class Builder {
		private String name;
		private List<String> lore;
		private List<Yml_Enchantment> enchantments;
		private List<String> flags;
		
		public Builder(){
			this.lore = new ArrayList<String>();
			this.enchantments = new ArrayList<Yml_Enchantment>();
			this.flags = new ArrayList<String>();
		}
		
		public Builder withName(String name){
			this.name = name;
			return this;
		}
		
		public Builder addLore(String lore){
			this.lore.add(lore);
			return this;
		}
		
		public Builder withEnchantment(Yml_Enchantment enchantment){
			this.enchantments.add(enchantment);
			return this;
		}
		
		public Builder addFlag(String flag){
			this.flags.add(flag);
			return this;
		}
		
		public Yml_ItemMeta build(){
			return new Yml_ItemMeta(this);
		}
	}

}
