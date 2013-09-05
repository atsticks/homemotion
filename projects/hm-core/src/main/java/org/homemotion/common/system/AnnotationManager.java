package org.homemotion.common.system;

import java.util.Map;
import java.util.Set;


public interface AnnotationManager {

	public Map<String, Set<String>> getAnnotationIndex();

	public Set<String> getClassAnnotations(String classname);

	public Set<String> getAnnotatedClasses(String annotation);

	public Set<String> getMethodAnnotatedClasses(String annotation);

	public void printAnnotations();

}
