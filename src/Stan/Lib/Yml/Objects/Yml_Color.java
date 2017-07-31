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
	
	public Yml_Color(){
		
	}
	
	private Yml_Color(Builder builder){
		this.red = builder.red;
		this.green = builder.green;
		this.blue = builder.blue;
	}

	public Color getColor() {
		return Color.fromRGB(red.getAmount(), green.getAmount(), blue.getAmount());
	}
	
	public static class Builder{
		private Yml_Amount red;
		private Yml_Amount green;
		private Yml_Amount blue;
		
		public Builder red(Yml_Amount red){
			this.red = red;
			return this;
		}
		
		public Builder green(Yml_Amount green){
			this.green = green;
			return this;
		}
		
		public Builder blue(Yml_Amount blue){
			this.blue = blue;
			return this;
		}
		
		public Yml_Color build(){
			return new Yml_Color(this);
		}
	}

}
