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
}
