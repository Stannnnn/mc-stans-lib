//Version 1.0 (29-7-2016)
package Stan.Lib.Yml.Objects;

import java.util.Random;

import Stan.Lib.Yml.Annotations.YmlClass;
import Stan.Lib.Yml.Annotations.YmlObject;

@YmlClass(merge = true)
public class Yml_Money {

	@YmlObject
	private double min;

	@YmlObject
	private double max;

	public double getAmount() {
		if (min == max) {
			return min;
		}

		try {
			double a = Math.min(min, max);
			double b = Math.max(min, max);
			return a + new Random().nextInt((int) Math.round(b - a));
		} catch (Exception ex) {
			return 0;
		}
	}

}
