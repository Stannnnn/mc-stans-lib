package Stan.Lib.Yml.Objects;

import Stan.Lib.Yml.Annotations.YmlObject;

public class Yml_Object {

	@YmlObject(skip = true)
	private String object;

	public String getObject() {
		return object;
	}

	@Override
	public String toString() {
		return "Yml_Object [object=" + object + "]";
	}

}
