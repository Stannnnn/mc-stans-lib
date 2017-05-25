package Stan.Lib.Yml.Objects;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Pattern {

	@YmlObject
	private Yml_Color color;

	@YmlObject
	private String patternType;

	public Pattern getPattern() {
		if (color != null && patternType != null) {
			return new Pattern(DyeColor.getByColor(color.getColor()), PatternType.valueOf(patternType));
		}
		return null;
	}

}
