package com.zaiquiri.janus;

import java.lang.reflect.Method;

public class SingleTestRunnerFactory implements TestRunnerFactory {

    private final Object interfaceTest;
    private final TestNotifierFactory testNotifierFactory;

    public SingleTestRunnerFactory(final Object interfaceTest, final TestNotifierFactory testNotifierFactory) {
        this.interfaceTest = interfaceTest;
        this.testNotifierFactory = testNotifierFactory;
    }

    @Override
    public Tester createTester(final Method test) {
        return new SingleTestRunner(test, interfaceTest, testNotifierFactory.createNotifier(test));
    }
}
