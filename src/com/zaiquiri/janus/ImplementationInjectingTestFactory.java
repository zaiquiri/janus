package com.zaiquiri.janus;

import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public class ImplementationInjectingTestFactory implements TestFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final RunNotifier notifier;
    private final Collection<Method> testMethods;

    public ImplementationInjectingTestFactory(final Object testClass, final Collection<Method> testMethods, final Field fieldToInject, final RunNotifier notifier) {
        this.interfaceUnderTest = fieldToInject;
        this.testClassInstance = testClass;
        this.notifier = notifier;
        this.testMethods = testMethods;
    }

    @Override
    public Tester createTester(final Object implementationOfInterface) {
        final Injector injector
                = new FieldInjector(interfaceUnderTest, testClassInstance, implementationOfInterface);

        final TestNotifierFactory testNotifierFactory
                = new JUnitTestNotifierFactory(implementationOfInterface, notifier);

        final TesterFromMethodFactory testFactory
                = new MethodTesterFromMethodFactory(testClassInstance, testNotifierFactory);

        return new FieldInjectingTestSuite(testMethods, testFactory, injector);
    }
}
