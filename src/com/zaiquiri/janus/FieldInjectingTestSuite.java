package com.zaiquiri.janus;

import java.lang.reflect.Method;
import java.util.Collection;

public class FieldInjectingTestSuite implements Tester {
    private final Collection<Method> tests;
    private final TesterFromMethodFactory testerFactory;
    private final Injector injector;

    public FieldInjectingTestSuite(final Collection<Method> tests, final TesterFromMethodFactory testerFactory, final Injector injector) {
        this.tests = tests;
        this.testerFactory = testerFactory;
        this.injector = injector;
    }

    @Override
    public final void runAllTests() {
        for (final Method test : tests) {
            injectInstance();
            //before
            runTest(test);
            //after
        }
    }

    private void runTest(final Method test) {
        testerFactory.createTester(test).runAllTests();
    }

    private void injectInstance() {
        injector.injectInstance();
    }
}
