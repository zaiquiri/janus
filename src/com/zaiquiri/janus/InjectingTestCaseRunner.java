package com.zaiquiri.janus;

public class InjectingTestCaseRunner implements Runnable {
    private final Runnable testCaseRunner;
    private final Injector injector;

    public InjectingTestCaseRunner(final Runnable testCaseRunner, final Injector injector) {
        this.testCaseRunner = testCaseRunner;
        this.injector = injector;
    }

    @Override
    public void run() {
        injector.injectInstance();
        testCaseRunner.run();
    }
}