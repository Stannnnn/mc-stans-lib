package Stan.Lib.Yml.Objects;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Value {

	@YmlObject(skip = true)
	private String value;

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Yml_Value [value=" + value + "]";
	}

}
