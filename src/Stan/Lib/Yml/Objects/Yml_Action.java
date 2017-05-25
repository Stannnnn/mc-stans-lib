package Stan.Lib.Yml.Objects;

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

}