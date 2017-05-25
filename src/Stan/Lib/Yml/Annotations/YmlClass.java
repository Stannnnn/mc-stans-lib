package Stan.Lib.Yml.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface YmlClass {
	// If the fields inside this class are the same put them under the parents name
	public boolean merge() default false;

	public Class<?>[] extensions() default Object.class;
}
