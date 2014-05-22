package com.zaiquiri.janus;

import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;

public class InjectingInstanceTestRunnerFactory implements InstanceTestRunnerFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final RunNotifier notifier;
    private final TestSuite testCases;

    public InjectingInstanceTestRunnerFactory(final TestClassData testClassData, final RunNotifier notifier) {
        this.testClassInstance = testClassData.testClass();
        this.testCases = testClassData.testSuite();
        this.interfaceUnderTest = testClassData.interfaceUnderTest();
        this.notifier = notifier;
    }

    @Override
    public Runnable createRunnerFor(final Object implementationOfInterface) {
        final Injector injector
                = new FieldInjector(interfaceUnderTest, testClassInstance, implementationOfInterface);
        final TestNotifierFactory testNotifierFactory
                = new JUnitTestNotifierFactory(implementationOfInterface, notifier);
        final TestCaseRunnerFactory testCaseRunnerFactory
                = new InjectingTestCaseRunnerFactory(testClassInstance, testNotifierFactory, injector);
        return new TestSuiteRunner(testCases, testCaseRunnerFactory);
    }
}
