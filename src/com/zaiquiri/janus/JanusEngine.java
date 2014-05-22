package com.zaiquiri.janus;

import java.lang.reflect.Field;
import java.util.Collection;

public class JanusEngine {
    private final TestClassData testClassData;

    public JanusEngine(final TestClassData testClassData) {
        this.testClassData = testClassData;
    }

    public void runWith(final TestNotifierFactory testNotifierFactory) {
        createSystemTester(testNotifierFactory).run();
    }

    private SystemTester createSystemTester(final TestNotifierFactory testNotifierFactory) {
        final String basePackage = testClassData.basePackage();
        final Field interfaceUnderTest = testClassData.interfaceUnderTest();

        final InstanceTesterFactory instanceTesterFactory = new DefaultInstanceTesterFactory(testClassData, testNotifierFactory);
        final ImplementationTesterFactory implementationTesterFactory = new DefaultImplementationTesterFactory(instanceTesterFactory);

        final Collection<Class<?>> allImplementations = new ClassFinder(basePackage).findImplementationsOf(interfaceUnderTest.getType());
        return new SystemTester(allImplementations, instanceMaker(), implementationTesterFactory);
    }

    private InstanceMaker instanceMaker() {
        return new InstanceMaker(new ConstructorStrategy(), new FactoryStrategy());
    }

    public String getName() {
        return testClassData.getName();
    }
}
