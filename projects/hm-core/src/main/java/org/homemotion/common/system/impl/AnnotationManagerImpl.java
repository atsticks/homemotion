package org.homemotion.common.system.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.homemotion.common.system.AnnotationManager;
import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnnotationManagerImpl implements AnnotationManager{

    private AnnotationDB annotationDB = createEmptyDB();
    private AnnotationDB annotationDBMethodOnly = createEmptyDB();
    private AnnotationDB annotationDBClassOnly = createEmptyDB();
    private static String[] ignoredPackages = new String[]{"javax", "java",
        "sun", "com.sun", "javassist", "org.jboss", "org.eclipse",
        "org.apache", "org.homemotion.dao"};

    public AnnotationManagerImpl() {
        try {
            LoggerFactory.getLogger(getClass()).info("Scanning annotations...");
            URL[] urls = ClasspathUrlFinder.findClassPaths();
            annotationDB.scanArchives(urls);
            annotationDBClassOnly.setScanClassAnnotations(true);
            annotationDBClassOnly.setScanFieldAnnotations(false);
            annotationDBClassOnly.setScanMethodAnnotations(false);
            annotationDBClassOnly.setScanParameterAnnotations(false);
            annotationDBClassOnly.scanArchives(urls);
            annotationDBMethodOnly.setScanClassAnnotations(false);
            annotationDBMethodOnly.setScanFieldAnnotations(false);
            annotationDBMethodOnly.setScanMethodAnnotations(true);
            annotationDBMethodOnly.setScanParameterAnnotations(false);
            annotationDBMethodOnly.scanArchives(urls);
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass())
                    .error("Failed to load annotations.", e);
        }
    }

    private AnnotationDB createEmptyDB() {
        AnnotationDB annotationDB = new AnnotationDB();
        annotationDB.setIgnoredPackages(ignoredPackages);
        return annotationDB;
    }

    public Map<String, Set<String>> getAnnotationIndex() {
        return annotationDB.getAnnotationIndex();
    }

    public Set<String> getClassAnnotations(String classname) {
        return annotationDB.getClassIndex().get(classname);
    }

    public Set<String> getAnnotatedClasses(String annotation) {
        Map<String, Set<String>> index = annotationDBClassOnly
                .getAnnotationIndex();
        return index.get(annotation);
    }

    public Set<String> getMethodAnnotatedClasses(String annotation) {
        Map<String, Set<String>> index = annotationDBMethodOnly
                .getAnnotationIndex();
        return index.get(annotation);
    }

    public void printAnnotations() {
        System.out.println("***** Homemotion Annotations found. *****");
        System.out.println("  - ignored packages: "
                + Arrays.toString(ignoredPackages));
        annotationDB
                .outputAnnotationIndex(new PrintWriter(System.out));
    }

}
