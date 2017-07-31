package Stan.Lib.Yml;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Defaults;

import Stan.Lib.Main;
import Stan.Lib.Conversion.StringUtils;
import Stan.Lib.Utils.Data;
import Stan.Lib.Yml.Annotations.YmlClass;
import Stan.Lib.Yml.Annotations.YmlObject;

/*
 * POSSIBLE FEATURES / BUGS:
 * - Show messages when strings are found in the config but that aren't required for the object.
 * - Unsure if enums require the YmlObject tag to be saved and loaded.
 */

/*
 * NOTE:
 * When checking if the values are correct it is important that there are no circular dependencies.
 * Also the fields have to be in the correct load order in the class because of how the verification mechanism works.
 */

public class YmlUtils {
	
	public static Object get(Data data, String configurationSection, Class<? extends Object> object) {
		return get(data.getFileName(), data.getData(), configurationSection, object, false);
	}
	
	public static Object get(Data data, String configurationSection, Class<? extends Object> object, boolean isArray) {
		return get(data.getFileName(), data.getData(), configurationSection, object, isArray);
	}
	
	public static Object get(String fileName, YamlConfiguration yamlConfiguration, String configurationSection, Class<? extends Object> object) {
		return get(fileName, yamlConfiguration, configurationSection, object, false);
	}
	
	public static Object get(String fileName, YamlConfiguration yamlConfiguration, String configurationSection, Class<? extends Object> object, boolean isArray) {
		ConfigurationSection cs = yamlConfiguration.getConfigurationSection(configurationSection);
		if (cs == null){
			Main.getInstance().printFailure("Missing Section '" + configurationSection + "' in '" + yamlConfiguration.getCurrentPath() + "' using template '" + getTemplateName(object.getTypeName()) + "'");
			return null;
		}
		return get(fileName, cs, object, isArray);
	}

	private static Object get(String fileName, ConfigurationSection cs, Class<? extends Object> object, boolean isArray) {
		if (!isArray){
			return get(fileName, cs, object, null);
		}else{
			ArrayList<Object> aL = new ArrayList<>();
			
			for (String key : cs.getKeys(false)){
				Object obj = get(fileName, cs, object, key);
				if (obj != null){
					aL.add(obj);
				}
			}
			return aL;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object get(String fileName, ConfigurationSection cs, Class<? extends Object> object, String prefix) {
		if (Enum.class.isAssignableFrom(object)){
			if (cs.contains(prefix)) {
				return Enum.valueOf((Class) object, cs.getString(prefix));
			}else{
				return null;
			}
		}
		
		Constructor<? extends Object> constructor = null;
		Object instance = null;
		
		try {
			if (Modifier.isAbstract(object.getModifiers())){
				Main.getInstance().printFailure("Missing value '" + cs.getCurrentPath() + (cs.getCurrentPath().isEmpty() || prefix.isEmpty() ? "" : ("." + prefix)) + "' in '" + fileName + "' using template '" + getTemplateName(object.getTypeName()) + "'");
				return null;
			}
			
			constructor = object.getConstructor();
			instance = constructor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (instance == null) {
			return null;
		}
		
		for (Field field : instance.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(YmlObject.class)) {
				if (isPrimitiveOrWrapperOrObject(field.getType()) && field.getAnnotation(YmlObject.class).extensions().length == 1) {
					String path = getPath(cs, field, prefix);
					
					if (path.equals("ConfigurationSection")){
						setField(fileName, cs, field, instance, cs.getCurrentPath(), prefix);
					} else if (cs.contains(path)) {
						setField(fileName, cs, field, instance, cs.get(path), prefix);
					}
					
					//System.out.println("a: " + field);
					
					if (!checkRequired(fileName, cs, instance, field, prefix)){
						return null;
					}
					
				} else {
					if (Iterable.class.isAssignableFrom(field.getType())) {
						Class<?> targetType = getIterableType(field);
						
						String path = getPath(cs, field, prefix);
						
						if (String.class.isAssignableFrom(targetType)){
							setField(fileName, cs, field, instance, cs.getStringList(path), prefix);
						} else if (targetType != null) {
							List<Object> list = new ArrayList<>();

							ConfigurationSection cs2 = cs.getConfigurationSection(path);
							
							if (cs2 != null) {
								for (String s : cs2.getKeys(false)) {
									Object result = get(fileName, cs, targetType, path + "." + s);
									
									if (result != null) {
										list.add(result);
									}
								}
							}
							setField(fileName, cs, field, instance, list, prefix);
						}
						
						//System.out.println("b: " + field);
						
						if (!checkRequired(fileName, cs, instance, field, prefix)){
							return null;
						}

					} else {
						Class<? extends Object> targetType = null;
						
						Class<? extends Object>[] ext = field.getAnnotation(YmlObject.class).extensions();
						
						if (ext.length != 1 && ext[0] != Object.class){
							ext = field.getAnnotation(YmlObject.class).extensions();
						}else{
							if (field.getType().isAnnotationPresent(YmlClass.class)) {
								ext = field.getType().getAnnotation(YmlClass.class).extensions();
							}else{
								ext = null;
							}
						}
						
						if (ext != null){
							Class<?> current = null;
							int mhits = 0;
							for (Class<?> a : ext) {
								int hits = 0;
								int maxfields = 0;
								
								for (Field f2 : a.getDeclaredFields()) {
									if (f2.isAnnotationPresent(YmlObject.class)) {
										maxfields++;
										
										String path = getPath(cs, f2, prefix, field);
										
										if (!path.isEmpty()){
											if (cs.contains(path)) {
												hits++;
											}
										}
									}
								}

								if (hits > mhits) {
									mhits = hits;
									current = a;
									
									if (hits == maxfields){
										break;
									}
								}
							}

							if (current != null) {
								targetType = current;
							}
						}

						if (targetType == null) {
							targetType = field.getType();
						}
						
						String path = getPath(cs, field, prefix);
						
						//System.out.println(path + " = " + targetType);
						
						Object result = get(fileName, cs, targetType, path);
						if (result != null) {
							setField(fileName, cs, field, instance, result, prefix);
						}
						
						//System.out.println("c: " + field);
						
						if (!checkRequired(fileName, cs, instance, field, prefix)){
							return null;
						}
					}
				}
			}
		}
		
		return instance;
	}

	private static void setField(String fileName, ConfigurationSection cs, Field field, Object instance, Object value, String prefix) {
		field.setAccessible(true);
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException e) {
			String path = getPath(cs, field, prefix);
			Main.getInstance().printFailure("Failed to load '" + cs.getCurrentPath() + (cs.getCurrentPath().isEmpty() || path.isEmpty() ? "" : ".") + path + "' from '" + fileName + "' expected '" + field.getType() + "' but got '" + value.getClass().getTypeName() + "'");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean set(Data data, String configurationSection, Object object){
		return set(data.getData(), configurationSection, object);
	}
	
	public static boolean set(YamlConfiguration yamlConfiguration, String configurationSection, Object object){
		ConfigurationSection cs;
		
		if (yamlConfiguration.isConfigurationSection(configurationSection)){
			cs = yamlConfiguration.getConfigurationSection(configurationSection);
		}else{
			cs = yamlConfiguration.createSection(configurationSection);
		}
		
		return set(cs, object);
	}

	private static boolean set(ConfigurationSection cs, Object object) {
		if (Iterable.class.isAssignableFrom(object.getClass())) {
			Iterable<?> iterable = (Iterable<?>) object;
			Iterator<?> iterator = iterable.iterator();
			int i = 0;
			while (iterator.hasNext()) {
				set(cs, iterator.next(), String.valueOf(i++));
			}
			return true;
		}else{
//				new IllegalArgumentException("So you're telling me this is an array? You're crazy!").printStackTrace();
			return set(cs, object, null);
		}
	}

	private static boolean set(ConfigurationSection cs, Object object, String prefix) {
		if (object != null) {
			if (Enum.class.isAssignableFrom(object.getClass())){
				cs.set(prefix, ((Enum<?>)object).name());
				return true;
			}
			
			Map<String, ObjWrap> map = new HashMap<>();

			Class<?> a = object.getClass();

			while (a != null) {
				for (Field field : a.getDeclaredFields()) {
					if (field.isAnnotationPresent(YmlObject.class)) {
						field.setAccessible(true);
						
						Object value;
						
						try {
							value = field.get(object);
						} catch (IllegalArgumentException | IllegalAccessException e) {
							e.printStackTrace();
							continue;
						}

						String path = getPath(cs, field, prefix);
						
						if (path.equals("ConfigurationSection")){
							continue;
						}

						if (isPrimitiveOrWrapperOrObject(field.getType())) {
							if (field.getDeclaringClass().isAnnotationPresent(YmlClass.class) && field.getDeclaringClass().getAnnotation(YmlClass.class).merge()) {
								map.put(path, new ObjWrap(field.getType(), value));
							} else {
								_set(cs, path, new ObjWrap(field.getType(), value));
							}
						} else {
							if (Iterable.class.isAssignableFrom(field.getType())) {
								Class<?> targetType = getIterableType(field);

								// is StringList?
								if (String.class.isAssignableFrom(targetType)) {
									Iterable<?> iterable = (Iterable<?>) value;
									if (iterable == null || (iterable != null && iterable.iterator().hasNext())) {
										cs.set(path, value);
									}
								} else {
									Iterable<?> iterable = (Iterable<?>) value;
									if (iterable != null) {
										Iterator<?> iterator = iterable.iterator();
										int i = 0;
										while (iterator.hasNext()) {
											set(cs, iterator.next(), path + "." + i++);
										}
									} else {
										set(cs, null, path);
									}
								}
							} else {
								set(cs, value, path);
							}
						}
					}
				}

				a = a.getSuperclass();
			}

			if (!map.isEmpty()) {
				boolean same = true;
				ObjWrap value = null;

				for (String key : map.keySet()) {
					if (value == null) {
						value = map.get(key);
					}

					if (!map.get(key).Obj.equals(value.Obj)) {
						same = false;
					}
				}
				
				if (same) {
					_set(cs, prefix, value, true);
				} else {
					for (String key : map.keySet()) {
						_set(cs, key, map.get(key));
					}
				}
			}
		}
		
		return true;
	}

	private static void _set(ConfigurationSection cs, String path, ObjWrap objWrap) {
		_set(cs, path, objWrap, false);
	}

	private static void _set(ConfigurationSection cs, String path, ObjWrap objWrap, boolean checkParent) {
		if (objWrap.Obj != null && !objWrap.Obj.equals(Defaults.defaultValue(objWrap.Type))) {
			cs.set(path, objWrap.Obj);
		} else {
			cs.set(path, null);

			if (checkParent) {
				if (cs.getConfigurationSection(getParent(path)).getKeys(false).isEmpty()){
					cs.set(getParent(path), null);
				}
			}
		}
	}
	
	private static String getPath(ConfigurationSection cs, Field field, String prefix) {
		return getPath(cs, field, prefix, null);
	}

	private static String getPath(ConfigurationSection cs, Field field, String prefix, Field offset) {
		String path = prefix == null ? "" : prefix;
		
		if (offset != null){
			if (!offset.getAnnotation(YmlObject.class).skip()){
				path += (path.isEmpty() ? "" : ".") + getFFN(offset);
			}
		}

		if (!field.getAnnotation(YmlObject.class).skip()) {
			String epath = path.isEmpty() ? "" : path + ".";

			if (!field.getAnnotation(YmlObject.class).path().isEmpty()) {
				epath += StringUtils.upperFirst(field.getAnnotation(YmlObject.class).path());
			} else {
				epath += getFFN(field);
			}

			if (!(!cs.contains(epath) && field.getDeclaringClass().isAnnotationPresent(YmlClass.class) && field.getDeclaringClass().getAnnotation(YmlClass.class).merge())) {
				path = epath;
			}
		}

		return path;
	}
	
	private static boolean checkRequired(String fileName, ConfigurationSection cs, Object instance, Field object, String prefix){
		if (object.isAnnotationPresent(YmlObject.class)){
			if (object.getAnnotation(YmlObject.class).verify()){
				if (object.getAnnotation(YmlObject.class).customVerify()){
					try {
						if (!(boolean) instance.getClass().getDeclaredMethod("verify" + getFFN(object)).invoke(instance)){
							sendRequiredMessage(fileName, cs, object, prefix);
							return false;
						}
					} catch (NoSuchMethodException e) {
						new IllegalArgumentException("So you're telling me to call a custom method but you haven't implemented it..").printStackTrace();
					} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}else{
					try {
						object.setAccessible(true);
						Object obj = object.get(instance);
						if (obj == null){
							sendRequiredMessage(fileName, cs, object, prefix);
							return false;
						} else if ("[]".equals(obj.toString())){
							sendRequiredMessage(fileName, cs, object, prefix);
							return false;
						} else {
							String path = getPath(cs, object, prefix);
							if (!cs.contains(path)){
								sendRequiredMessage(fileName, cs, object, path);
								return false;
							}
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return true;
	}
	
	private static void sendRequiredMessage(String fileName, ConfigurationSection cs, Field object, String prefix){
		Main.getInstance().printFailure("Verification failed for value '" + getFFN(object) + "' under '"+ cs.getCurrentPath() + (cs.getCurrentPath().isEmpty() || prefix == null || prefix.isEmpty() ? "" : ("." + prefix)) + "' in '" + fileName + "' using template '" + getTemplateName(getPath(cs, object, prefix)) + "'");
	}

	private static Class<?> getIterableType(Field field) {
		return (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
	}

	private static String getParent(String path) {
		if (path.contains(".")) {
			return path.substring(0, path.lastIndexOf("."));
		}
		return path;
	}
	
	private static String getTemplateName(String path){
		if (path.contains(".")) {
			return path.substring(path.lastIndexOf(".") + 1);
		}
		return path;
	}

	// getFancyFieldName
	private static String getFFN(Field field) {
		return StringUtils.upperFirst(field.getName());
	}
	
	// reverseFancyFieldName
	@SuppressWarnings("unused")
	private static String reverseFFN(String name){
		return StringUtils.lowerFirst(getTemplateName(name));
	}

	private static boolean isPrimitiveOrWrapperOrObject(Class<?> check) {
		return ClassUtils.isPrimitiveOrWrapper(check) || check.getName().equals("java.lang.String") /*|| check.getName().equals("java.lang.Object")*/;
	}

}

class ObjWrap {
	public Class<?> Type;
	public Object Obj;

	public ObjWrap(Class<?> type, Object obj) {
		Type = type;
		Obj = obj;
	}
}