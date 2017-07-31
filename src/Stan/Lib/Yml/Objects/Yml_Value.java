package Stan.Lib.Yml.Objects;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Value {

	@YmlObject(skip = true)
	private String value;
	
	public Yml_Value(){
		
	}
	
	private Yml_Value(Builder builder){
		this.value = builder.value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Yml_Value [value=" + value + "]";
	}
	
	public static class Builder{
		private String value;
		
		public Builder value(String value){
			this.value = value;
			return this;
		}
		
		public Yml_Value build(){
			return new Yml_Value(this);
		}
		
	}

}
