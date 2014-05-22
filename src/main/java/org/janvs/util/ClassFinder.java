package org.janvs.util;

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

    public Collection<Class<?>> findImplementationsOf(final Class interfase) {
        final Set<String> allPackages = findAllPackages();
        final Collection<Class<?>> allImplementors = new HashSet<>();

        for (final String packageName : allPackages){
            final Reflections reflector = new Reflections(forPackage(packageName));
            @SuppressWarnings("unchecked")
            final Set<Class<?>> implementors = (Set<Class<?>>) reflector.getSubTypesOf(interfase);
            allImplementors.addAll(implementors);
        }

        return allImplementors;
    }

    private Set<String> findAllPackages() {
        final List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        final Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[classLoadersList.size()])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(basePackage))));
        final Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        final Set<String> packageNameSet = new TreeSet<>();
        for (final Class classInstance : classes) {
            packageNameSet.add(classInstance.getPackage().getName());
        }
        return packageNameSet;
    }
}
