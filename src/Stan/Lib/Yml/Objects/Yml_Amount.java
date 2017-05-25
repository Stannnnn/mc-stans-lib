//Version 1.0 (29-7-2016)
package Stan.Lib.Yml.Objects;

import java.util.Random;

import Stan.Lib.Yml.Annotations.YmlClass;
import Stan.Lib.Yml.Annotations.YmlObject;

@YmlClass(merge = true)
public class Yml_Amount {

	@YmlObject
	private int min = 1;

	@YmlObject
	private int max = 1;

	public int getAmount() {
		if (min == max) {
			return min;
		}

		try {
			int a = Math.min(min, max);
			int b = Math.max(min, max);
			return a + new Random().nextInt(b - a);
		} catch (Exception ex) {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Yml_Amount [min=" + min + ", max=" + max + "]";
	}

}
