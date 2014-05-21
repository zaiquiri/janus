package com.zaiquiri.janus;

import java.lang.reflect.Method;
import java.util.Collection;

public class InjectingTester implements Tester {
    private final Collection<Method> tests;
    private final TestRunnerFactory testerFactory;
    private final Injector injector;

    public InjectingTester(final Collection<Method> tests, final TestRunnerFactory testerFactory, final Injector injector) {
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
