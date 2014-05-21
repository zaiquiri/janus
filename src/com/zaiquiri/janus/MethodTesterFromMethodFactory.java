package com.zaiquiri.janus;

import java.lang.reflect.Method;

public class MethodTesterFromMethodFactory implements TesterFromMethodFactory {

    private final Object interfaceTest;
    private final TestNotifierFactory testNotifierFactory;

    public MethodTesterFromMethodFactory(final Object interfaceTest, final TestNotifierFactory testNotifierFactory) {
        this.interfaceTest = interfaceTest;
        this.testNotifierFactory = testNotifierFactory;
    }

    @Override
    public Tester createTester(final Method test) {
        return new MethodTester(testNotifierFactory.createNotifier(test), test, interfaceTest);
    }
}
