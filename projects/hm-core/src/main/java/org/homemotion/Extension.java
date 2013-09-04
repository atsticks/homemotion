package org.homemotion;

public @interface Extension {
	Class target() default Object.class;
}
