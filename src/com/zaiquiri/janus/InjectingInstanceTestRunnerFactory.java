package com.zaiquiri.janus;

import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Field;
import java.util.Collection;

public class InjectingInstanceTestRunnerFactory implements InstanceTestRunnerFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final RunNotifier notifier;
    private final Collection<TestCase> testCases;

    public InjectingInstanceTestRunnerFactory(final TestClassData testClassData, final RunNotifier notifier) {
        this.testClassInstance = testClassData.testClass();
        this.testCases = testClassData.testMethods();
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
