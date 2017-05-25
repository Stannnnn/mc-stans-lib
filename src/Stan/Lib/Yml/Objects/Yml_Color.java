package Stan.Lib.Yml.Objects;

import org.bukkit.Color;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Color {

	@YmlObject
	private Yml_Amount red;

	@YmlObject
	private Yml_Amount green;

	@YmlObject
	private Yml_Amount blue;

	public Color getColor() {
		return Color.fromRGB(red.getAmount(), green.getAmount(), blue.getAmount());
	}

}
