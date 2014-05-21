package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Janus extends Runner {
    private final Class<?> clazz;
    private final Object testClass;
    private final AnnotationReader annotationReader;

    public Janus(final Class<?> clazz) throws InstantiationException, IllegalAccessException {
        this.clazz = clazz;
        testClass = clazz.newInstance();
        annotationReader = new AnnotationReader(clazz);
    }

    @Override
    public void run(final RunNotifier notifier) {
        final List<Method> testMethods = annotationReader.getTestMethods();
        final Field interfaceUnderTest = annotationReader.getInterfaceUnderTest();
        final String basePackage = annotationReader.getBasePackageUnderTest();

        final TestClassData testClassData = new TestClassData(testClass, testMethods, interfaceUnderTest);
        final TestFactory testFactory = new JanusTestFactory(testClassData, notifier);
        final JanusTestEngine janusTestEngine = new JanusTestEngine(basePackage, testFactory);

        janusTestEngine.testAllPossibleImplementationsOf(interfaceUnderTest);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
    }


}