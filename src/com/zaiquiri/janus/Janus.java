package com.zaiquiri.janus;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Janus extends Runner {
    private final Class<?> clazz;
    private final List<Method> tests;
    private final Object interfaceTest;
    private final Field interfaceUnderTest;

    public Janus(final Class<?> clazz) throws InstantiationException, IllegalAccessException {
        this.clazz = clazz;
        tests = getTestMethods();
        interfaceTest = clazz.newInstance();
        interfaceUnderTest = findInterfaceUnderTest();
    }

    @Override
    public void run(final RunNotifier notifier) {
        runForAllImplementors(notifier);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Options {
        String basePackage();
    }

    private void runForAllImplementors(final RunNotifier notifier) {
        for (final Class<?> implementor : getImplementors()) {
            runForImplementor(notifier, implementor);
        }
    }

    private Collection<Class<?>> getImplementors() {
        ClassFinder classFinder = getClassFinderForClass(clazz);
        return classFinder.findImplementationsOf(interfaceUnderTest.getType());
    }

    private void runForImplementor(final RunNotifier notifier, final Class<?> implementor) {
        runForConstructors(notifier, implementor);
        runForFactories(notifier, implementor);
    }

    private void runForFactories(RunNotifier notifier, Class<?> implementation) {

    }

    private void runForConstructors(RunNotifier notifier, Class<?> implementation) {
        for (final Constructor constructor : implementation.getConstructors()) {
            runForAllInstancesOfConstructor(notifier, constructor);
        }
    }

    private void runForAllInstancesOfConstructor(RunNotifier notifier, Constructor constructor) {
        for (final Object instance : createAllInstancesFromConstructor(constructor)) {
            runAllTests(notifier, instance);
        }
    }

    private Iterable<Object> createAllInstancesFromConstructor(Constructor constructor) {
        List<Object> instances = new ArrayList<Object>();
        try {
            instances.add(constructor.newInstance());
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

    private void runAllTests(final RunNotifier notifier, final Object instance) {
        for (final Method test : tests) {
            runTest(notifier, instance, test);
        }
    }

    private void runTest(final RunNotifier notifier, final Object instance, final Method test) {
        final Description testDescription = Description.createTestDescription(instance.getClass(), test.getName());
        injectInstance(instance);
        final SingleTestRunner singleTestRunner = new SingleTestRunner(new JunitTestNotifier(notifier, testDescription), test, interfaceTest);
        singleTestRunner.evaluateTest();
    }

    private ClassFinder getClassFinderForClass(final Class<?> clazz) {
        for (final Annotation annotation : clazz.getAnnotations()) {
            if (annotation.annotationType().equals(Options.class)) {
                final Options options = (Options) annotation;
                return new ClassFinder(options.basePackage());
            }
        }
        throw new RuntimeException("Base package not defined in @Janus.Options annotation");
    }

    private Field findInterfaceUnderTest() throws IllegalArgumentException, IllegalAccessException {
        final Field[] fields = clazz.getDeclaredFields();
        for (final Field field : fields) {
            if (field.getAnnotation(UnderTest.class) != null) {
                return field;
            }
        }
        throw new RuntimeException("@UnderTest interface not found in test class");
    }

    private void injectInstance(Object instance) {
        final Exposer exposer = new Exposer();
        exposer.expose(interfaceUnderTest);
        try {
            interfaceUnderTest.set(interfaceTest, instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private List<Method> getTestMethods() {
        final ArrayList<Method> methods = new ArrayList<Method>();
        for (final Method method : clazz.getDeclaredMethods()) {
            if (isATestMethod(method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    private boolean isATestMethod(final Method method) {
        return method.getAnnotation(Test.class) != null && method.getAnnotation(Ignore.class) == null;
    }


}
