package Stan.Lib.Yml.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface YmlObject {
	public String path() default "";

	public boolean skip() default false;
	
	public boolean verify() default false;
	public boolean customVerify() default false;
	
	public Class<?>[] extensions() default Object.class;
}
