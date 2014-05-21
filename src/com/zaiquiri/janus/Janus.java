package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
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
        final List<TestCase> testCases = annotationReader.getTestCases();
        final Field interfaceUnderTest = annotationReader.getInterfaceUnderTest();
        final String basePackage = annotationReader.getBasePackageUnderTest();

        final TestClassData testClassData = new TestClassData(testClass, testCases, interfaceUnderTest);
        final InstanceTestRunnerFactory instanceTestRunnerFactory = new InjectingInstanceTestRunnerFactory(testClassData, notifier);
        final InstanceMaker instanceMaker = new InstanceMaker(new ConstructorStrategy(), new FactoryStrategy());

        final TestSuiteForInstacesFactory testSuiteForInstacesFactory = new TestSuiteForInstacesFactory(instanceTestRunnerFactory);
        final ClassFinder classFinder = new ClassFinder(basePackage);

        final ClassTestRunner classTestRunner = new ClassTestRunner(classFinder, instanceMaker, testSuiteForInstacesFactory);
        classTestRunner.testAllPossibleImplementationsOf(interfaceUnderTest);
    }

    @Override
    public Description getDescription() {
        return Description.createSuiteDescription(clazz.getName(), clazz.getAnnotations());
    }


}