package com.zaiquiri.janus;

import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public class JanusTestFactory implements TestFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final RunNotifier notifier;
    private final Collection<Method> testMethods;

    public JanusTestFactory(final TestClassData testClassData, final RunNotifier notifier) {
        this.testClassInstance = testClassData.testClass();
        this.testMethods = testClassData.testMethods();
        this.interfaceUnderTest = testClassData.interfaceUnderTest();
        this.notifier = notifier;
    }

    @Override
    public Tester createTester(final Object implementationOfInterface) {
        final Injector injector
                = new FieldInjector(interfaceUnderTest, testClassInstance, implementationOfInterface);

        final TestNotifierFactory testNotifierFactory
                = new JUnitTestNotifierFactory(implementationOfInterface, notifier);

        final TesterFromMethodFactory testFactory
                = new MethodTesterFromMethodFactory(testClassInstance, testNotifierFactory);

        return new InjectingTester(testMethods, testFactory, injector);
    }
}
