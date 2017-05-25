package Stan.Lib.Conversion;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtils {

	// Works for 'Player'
	public static Object runGetter(Object o, String fieldName) {
		try {
			return o.getClass().getSuperclass().getDeclaredMethod("get" + StringUtils.upperFirst(fieldName)).invoke(o);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

}
