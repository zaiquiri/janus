package com.zaiquiri.janus;

import java.lang.reflect.Method;
import java.util.List;

public class SuiteTestRunner implements Tester {
    private final List<Method> tests;
    private final TesterFactory testerFactory;
    private final Injector injector;

    public SuiteTestRunner(final List<Method> tests, final TesterFactory testerFactory, final Injector injector) {
        this.tests = tests;
        this.testerFactory = testerFactory;
        this.injector = injector;
    }

    @Override
    public final void test() {
        for (final Method test : tests) {
            injectInstance();
            //before
            runTest(test);
            //after
        }
    }

    private void runTest(final Method test) {
        testerFactory.createTester(test).test();
    }

    private void injectInstance() {
        injector.injectInstance();
    }
}
