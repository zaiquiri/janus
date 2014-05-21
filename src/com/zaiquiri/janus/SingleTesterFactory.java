package com.zaiquiri.janus;

import java.lang.reflect.Method;

public class SingleTesterFactory implements TesterFactory {

    private final Object interfaceTest;
    private final TestNotifierFactory testNotifierFactory;

    public SingleTesterFactory(final Object interfaceTest, final TestNotifierFactory testNotifierFactory) {
        this.interfaceTest = interfaceTest;
        this.testNotifierFactory = testNotifierFactory;
    }

    @Override
    public Tester createTester(final Method test) {
        return new SingleTestRunner(testNotifierFactory.createNotifier(test), test, interfaceTest);
    }
}
