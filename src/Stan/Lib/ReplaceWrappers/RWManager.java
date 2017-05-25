package Stan.Lib.ReplaceWrappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

import Stan.Lib.IManager;
import Stan.Lib.Conversion.ReflectionUtils;
import Stan.Lib.Conversion.StringUtils;

public class RWManager implements IManager {

	private Map<String, List<String>> replaceSources;

	public RWManager() {
		replaceSources = new HashMap<>();

		initializeDefaultReplaceSources();
	}

	@Override
	public void onDisable() {
		replaceSources = null;
	}

	private static ArrayList<Object> getRawRWs(Iterable<?> objects){
		ArrayList<Object> obj = new ArrayList<>();
		for (Object o : objects){
			if (o != null){
				if (Iterable.class.isAssignableFrom(o.getClass())){
					obj.addAll(getRawRWs((Iterable<?>) o));
				}else{
					obj.add(o);
				}
			}
		}
		return obj;
	}
	
	public RW[] getRWs(Object... objects) {
		ArrayList<Object> obj = getRawRWs(Arrays.asList(objects));
		
		RW[] rws = new RW[obj.size()];
		
		int i = 0;
		for (Object o : obj) {
			if (o instanceof RW) {
				rws[i] = (RW) o;
			} else {
				String c = getClassName(o.getClass());
				if (replaceSources.containsKey(c)){
					for (String s : replaceSources.get(c)) {
						Object value = ReflectionUtils.runGetter(o, s);
						rws[i] = new RW("{" + StringUtils.upperFirst(c) + StringUtils.upperFirst(s) + "}", value);
					}
				}
			}
			i++;
		}
		return rws;
	}

	public void addReplaceSource(Class<?> c, String replacements) {
		replaceSources.put(getClassName(c), Arrays.asList(replacements));
	}

	public void removeActionMethod(Class<?> c) {
		replaceSources.remove(getClassName(c));
	}

	private final void initializeDefaultReplaceSources() {
		replaceSources.put(getClassName(Player.class), Arrays.asList("Name"));
	}
	
	private String getClassName(Class<?> c){
		String s = c.getSimpleName();
		s = s.replace("Craft", "");
		return s;
	}

}
