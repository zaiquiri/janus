package com.zaiquiri.janus;

public class InjectingAspectTester implements Tester {
    private final Tester testCaseRunner;
    private final Injector injector;

    public InjectingAspectTester(final Tester testCaseRunner, final Injector injector) {
        this.testCaseRunner = testCaseRunner;
        this.injector = injector;
    }

    @Override
    public void run() {
        injector.injectInstance();
        testCaseRunner.run();
    }
}