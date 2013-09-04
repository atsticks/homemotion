package org.homemotion.common.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

@Qualifier
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
	@Nonbinding
	String channel() default "";

	@Nonbinding
	String evenType() default "";
}
