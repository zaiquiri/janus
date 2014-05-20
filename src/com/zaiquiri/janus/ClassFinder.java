package com.zaiquiri.janus;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.*;

import static org.reflections.util.ClasspathHelper.forPackage;

public class ClassFinder {

    private final String basePackage;

    public ClassFinder(final String basePackage) {
        this.basePackage = basePackage;
    }

    public Collection<Class<?>> findImplementationsOf(Class interfase) {

        Set<String> allPackages = findAllPackages();
        Collection<Class<?>> allImplementors = new HashSet<Class<?>>();

        for (String packageName : allPackages){
            Reflections reflector = new Reflections(forPackage(packageName));
            Set implementors = reflector.getSubTypesOf(interfase);
            allImplementors.addAll(implementors);
        }

        return allImplementors;
    }

    public Set<String> findAllPackages() {
        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage))));
        Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
        Set<String> packageNameSet = new TreeSet<String>();
        for (Class classInstance : classes) {
            packageNameSet.add(classInstance.getPackage().getName());
        }
        return packageNameSet;
    }
}
