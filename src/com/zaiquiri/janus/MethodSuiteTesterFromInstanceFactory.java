package com.zaiquiri.janus;

import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

public class MethodSuiteTesterFromInstanceFactory implements TesterFromInstanceFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final RunNotifier notifier;
    private final Collection<Method> testMethods;

    public MethodSuiteTesterFromInstanceFactory(final Field interfaceUnderTest, final Object testClassInstance, final RunNotifier notifier, final Collection<Method> testMethods) {
        this.interfaceUnderTest = interfaceUnderTest;
        this.testClassInstance = testClassInstance;
        this.notifier = notifier;
        this.testMethods = testMethods;
    }

    @Override
    public Tester createTester(final Object instance) {
        final Injector injector = new FieldInjector(interfaceUnderTest, testClassInstance, instance);
        final TestNotifierFactory testNotifierFactory = new JUnitTestNotifierFactory(instance, notifier);
        final TesterFromMethodFactory testerFactory = new MethodTesterFromMethodFactory(testClassInstance, testNotifierFactory);
        return new MethodSuiteTester(testMethods, testerFactory, injector);
    }
}
