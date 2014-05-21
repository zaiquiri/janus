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
import java.util.concurrent.atomic.AtomicBoolean;

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
        runForAllImplementors(notifier);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
    }

    private void runForAllImplementors(final RunNotifier notifier) {
        for (final Class<?> implementor : getImplementors()) {
            runForImplementor(implementor, notifier);
        }
    }

    private void runForImplementor(final Class<?> implementor, final RunNotifier notifier) {
        runForAllConstructors(implementor, notifier);
        // runForFactories(notifier, implementor);
    }

    private void runForAllConstructors(Class<?> implementation, RunNotifier notifier) {
        for (final Constructor constructor : implementation.getConstructors()) {
            runForAllInstancesOfConstructor(notifier, constructor);
        }
    }

    private void runForAllInstancesOfConstructor(final RunNotifier notifier, final Constructor constructor) {
        for (final Object instance : createAllInstancesFromConstructor(constructor)) {
            Injector injector = new Injector() {
                @Override
                public void injectInstance() {
                    final Exposer exposer = new Exposer();
                    exposer.expose(interfaceUnderTest);
                    try {
                        interfaceUnderTest.set(testClassInstance, instance);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            };
            TestNotifierFactory testNotifierFactory = new TestNotifierFactory() {
                @Override
                public TestNotifier createNotifier(Method test) {
                    final Description testDescription = Description.createTestDescription(instance.getClass(), test.getName());
                    return new JunitTestNotifier(notifier, testDescription);
                }
            };
            Tester suiteTester = new SuiteTestRunner(annotationReader.getTestMethods(), new SingleTesterFactory(testClassInstance, testNotifierFactory), injector);
            //beforeall
            suiteTester.test();
            //afterall
        }
    }


    private Collection<Class<?>> getImplementors() {
        ClassFinder classFinder = getClassFinderForBasePackage(clazz);
        return classFinder.findImplementationsOf(interfaceUnderTest.getType());
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

    private ClassFinder getClassFinderForBasePackage(final Class<?> clazz) {
        return new ClassFinder(annotationReader.getBasePackageUnderTest(clazz));
    }
}