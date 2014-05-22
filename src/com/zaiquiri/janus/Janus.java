package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;

public class Janus extends Runner {

    private final TestClassData testClassData;

    public Janus(final Class<?> testContainer) throws InstantiationException, IllegalAccessException {
        this.testClassData = new TestContainerReader(testContainer).getTestClassData();
    }

    @Override
    public void run(final RunNotifier notifier) {

        final String basePackage = testClassData.basePackage();
        final Field interfaceUnderTest = testClassData.interfaceUnderTest();

        final InstanceTestRunnerFactory instanceTestRunnerFactory = new InjectingInstanceTestRunnerFactory(testClassData, notifier);
        final InstanceMaker instanceMaker = new InstanceMaker(new ConstructorStrategy(), new FactoryStrategy());
        final TestSuiteForInstacesFactory testSuiteForInstacesFactory = new TestSuiteForInstacesFactory(instanceTestRunnerFactory);

        final JanusEngine janusEngine = new JanusEngine(new ClassFinder(basePackage), instanceMaker, testSuiteForInstacesFactory);
        janusEngine.testAllPossibleImplementationsOf(interfaceUnderTest);
    }

    @Override
    public Description getDescription() {
        final String name = testClassData.getName();
        return Description.createSuiteDescription(name);
    }


}