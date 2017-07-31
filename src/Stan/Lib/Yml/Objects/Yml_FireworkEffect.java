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
	
	public Yml_FireworkEffect(){
		
	}
	
	private Yml_FireworkEffect(Builder builder){
		this.flicker = builder.flicker;
		this.trail = builder.trail;
		this.colors = builder.colors;
		this.fadingColors = builder.fadingColors;
		this.type = builder.type;
	}

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
	
	public static class Builder{
		private boolean flicker;
		private boolean trail;
		private List<Yml_Color> colors;
		private List<Yml_Color> fadingColors;
		private String type;
		
		public Builder(){
			colors = new ArrayList<>();
			fadingColors = new ArrayList<>();
		}
		
		public Builder withFlicker(){
			flicker = true;
			return this;
		}
		
		public Builder withTrail(){
			trail = true;
			return this;
		}
		
		public Builder withColor(Yml_Color color){
			this.colors.add(color);
			return this;
		}
		
		public Builder withFadingColor(Yml_Color fadingColor){
			this.fadingColors.add(fadingColor);
			return this;
		}
		
		public Builder withType(String type){
			this.type = type;
			return this;
		}
		
		public Yml_FireworkEffect build(){
			return new Yml_FireworkEffect(this);
		}
	}
	
}
