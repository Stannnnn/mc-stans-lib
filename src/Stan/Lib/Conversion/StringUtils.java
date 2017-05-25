package Stan.Lib.Conversion;

public class StringUtils {

	public static String upperFirst(String s) {
		return s.isEmpty() ? "" : s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static String lowerFirst(String s) {
		return s.isEmpty() ? "" : s.substring(0, 1).toLowerCase() + s.substring(1);
	}
	
}
