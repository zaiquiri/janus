package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Janus extends Runner {
    private final Class<?> clazz;
    private final Object testClassInstance;
    private final Field interfaceUnderTest;
    private final AnnotationReader annotationReader;

    public Janus(final Class<?> clazz) throws InstantiationException, IllegalAccessException {
        this.clazz = clazz;
        testClassInstance = clazz.newInstance();
        annotationReader = new AnnotationReader(this.clazz);
        interfaceUnderTest = annotationReader.getInterfaceUnderTest();
    }

    @Override
    public void run(final RunNotifier notifier) {
        final TesterFromInstanceFactory testerFactory = new MethodSuiteTesterFromInstanceFactory(interfaceUnderTest, testClassInstance, notifier, annotationReader.getTestMethods());
        runForAllImplementors(testerFactory);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
    }






    private void runForAllImplementors(final TesterFromInstanceFactory testerFactory) {
        for (final Class<?> implementor : getImplementors()) {
            runForImplementor(implementor, testerFactory);
        }
    }

    private Collection<Class<?>> getImplementors() {
        ClassFinder classFinder = getClassFinderForBasePackage(clazz);
        return classFinder.findImplementationsOf(interfaceUnderTest.getType());
    }

    private void runForImplementor(final Class<?> implementor, final TesterFromInstanceFactory testerFactory) {
        runForAllConstructors(implementor, testerFactory);
        runForFactories(implementor, testerFactory);
    }

    private ClassFinder getClassFinderForBasePackage(final Class<?> clazz) {
        return new ClassFinder(annotationReader.getBasePackageUnderTest(clazz));
    }









    private void runForFactories(final Class<?> implementor, final TesterFromInstanceFactory testerFactory) {
        for (final Method factory : getFactories(implementor)) {
            Tester tester = new InstanceSuiteTester(testerFactory, createAllInstancesFromFactories(factory));
            tester.test();
        }
    }

    private Iterable<Object> createAllInstancesFromFactories(final Method factory) {
        return new ArrayList<Object>();
    }

    private Iterable<Method> getFactories(final Class<?> implementor) {
        return new ArrayList<Method>();
    }







    private void runForAllConstructors(Class<?> implementor, TesterFromInstanceFactory testerFactory) {
        for (final Constructor constructor : implementor.getConstructors()) {
            Tester tester = new InstanceSuiteTester(testerFactory, createAllInstancesFromConstructor(constructor));
            tester.test();
        }
    }

    private Iterable<Object> createAllInstancesFromConstructor(Constructor constructor) {
        List<Object> instances = new ArrayList<Object>();
        try {
            final Class[] parameterTypes = constructor.getParameterTypes();
            final Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];
                if (parameterType.isPrimitive()) {
                    parameters[i] = 0;
                }
            }
            instances.add(constructor.newInstance(parameters));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }


}