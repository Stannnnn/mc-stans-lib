package Stan.Lib.Yml.Objects;

import org.bukkit.enchantments.Enchantment;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Enchantment {

	@YmlObject
	private String name;

	@YmlObject
	private int level;

	@YmlObject
	private boolean ignoreRestrictions;

	public Enchantment getEnchantment() {
		return Enchantment.getByName(name);
	}

	public int getLevel() {
		return level;
	}

	public boolean getIgnoreRestrictions() {
		return ignoreRestrictions;
	}

	@Override
	public String toString() {
		return "Yml_Enchantment [name=" + name + ", level=" + level + ", ignoreRestrictions=" + ignoreRestrictions + "]";
	}

}
