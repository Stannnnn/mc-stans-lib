package Stan.Lib.Conversion;

import java.util.List;

public class CastUtils {

	public static boolean isInt(String s) {
		try {
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	
	public static boolean isArray(Object object){
		return object != null && Iterable.class.isAssignableFrom(object.getClass());
	}
	
	@SuppressWarnings("unchecked")
	public static List<Object> getArray(Object object){
		return (List<Object>) object;
	}
	
	public static String getString(Object object){
		if (object == null){
			return "";
		}
		
		if (object instanceof String){
			return (String) object;
		}
		
		return object.toString();
	}

}
