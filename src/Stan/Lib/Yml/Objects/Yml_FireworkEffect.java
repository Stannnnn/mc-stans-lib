package Stan.Lib.Yml.Objects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_FireworkEffect {

	@YmlObject
	private boolean flicker;
	
	@YmlObject
	private boolean trail;
	
	@YmlObject
	private List<Yml_Color> colors;
	
	@YmlObject
	private List<Yml_Color> fadingColors;
	
	@YmlObject
	private String type;

	public boolean isFlicker() {
		return flicker;
	}

	public boolean isTrail() {
		return trail;
	}

	public List<Color> getColors() {
		if (this.colors == null || this.colors.isEmpty()){
			return null;
		}
		
		List<Color> colors = new ArrayList<>();
		for (Yml_Color color : this.colors){
			colors.add(color.getColor());
		}
		return colors;
	}

	public List<Color> getFadingColors() {
		if (this.fadingColors == null){
			return null;
		}
		
		List<Color> colors = new ArrayList<>();
		for (Yml_Color color : this.fadingColors){
			colors.add(color.getColor());
		}
		return colors;
	}

	public Type getType() {
		return Type.valueOf(type);
	}
	
}
