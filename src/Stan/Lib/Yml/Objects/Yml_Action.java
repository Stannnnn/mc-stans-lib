package Stan.Lib.Yml.Objects;

import java.util.ArrayList;
import java.util.List;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Action {

	@YmlObject
	private String action;
	
	@YmlObject(extensions = { Yml_Value.class, Yml_Title.class, Yml_Firework.class })
	private Object value;

	@YmlObject
	private Yml_Requirements requirements;

	@YmlObject
	private List<Yml_Action> fail;
	
	public Yml_Action(){
		
	}
	
	private Yml_Action(Builder builder){
		this.action = builder.action;
		this.value = builder.value;
		this.requirements = builder.requirements;
		this.fail = builder.fail;
	}

	public String getAction() {
		return action;
	}

	public String getValue() {
		if (value instanceof Yml_Value){
			return ((Yml_Value) value).getValue();
		}
		
		return null;
	}
	
	public Object getActualValue(){
		return value;
	}

	public Yml_Requirements getRequirements() {
		return requirements;
	}

	public List<Yml_Action> getActionsOnFail() {
		return fail;
	}
	
	@Override
	public String toString() {
		return "Yml_Action [action=" + action + ", value=" + value + ", requirements=" + requirements + ", fail=" + fail + "]";
	}
	
	public static class Builder{
		private String action;
		private Object value;
		private Yml_Requirements requirements;
		private List<Yml_Action> fail;
		
		public Builder(){
			this.fail = new ArrayList<>();
		}
		
		public Builder action(String action){
			this.action = action;
			return this;
		}
		
		public Builder value(Object value){
			this.value = value;
			return this;
		}
		
		public Builder requirements(Yml_Requirements requirements){
			this.requirements = requirements;
			return this;
		}
		
		public Builder fail(Yml_Action fail){
			this.fail.add(fail);
			return this;
		}
		
		public Yml_Action build(){
			return new Yml_Action(this);
		}
	}

}