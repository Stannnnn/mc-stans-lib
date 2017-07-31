package Stan.Lib.Yml.Objects;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Title {

	@YmlObject
	private String title;

	@YmlObject
	private String subtitle;

	@YmlObject
	private int fadeIn;

	@YmlObject
	private int stay;

	@YmlObject
	private int fadeOut;
	
	public Yml_Title(){
		
	}
	
	private Yml_Title(Builder builder){
		this.title = builder.title;
		this.subtitle = builder.subtitle;
		this.fadeIn = builder.fadeIn;
		this.stay = builder.stay;
		this.fadeOut = builder.fadeOut;
	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}
	
	public static class Builder{
		private String title;
		private String subtitle;
		private int fadeIn;
		private int stay;
		private int fadeOut;
		
		public Builder title(String title){
			this.title = title;
			return this;
		}
		
		public Builder subtitle(String subtitle){
			this.subtitle = subtitle;
			return this;
		}
		
		public Builder fadeIn(int fadeIn){
			this.fadeIn = fadeIn;
			return this;
		}
		
		public Builder stay(int stay){
			this.stay = stay;
			return this;
		}
		
		public Builder fadeOut(int fadeOut){
			this.fadeOut = fadeOut;
			return this;
		}
		
		public Yml_Title build(){
			return new Yml_Title(this);
		}
		
	}
}
