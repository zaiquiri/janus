package org.janvs.testers;

import org.janvs.injectors.Injector;

public class InjectingAspectTester implements Tester {
    private final Tester testCaseRunner;
    private final Injector injector;

    public InjectingAspectTester(final Tester testCaseRunner, final Injector injector) {
        if (testCaseRunner == null || injector == null) {
            throw new NullPointerException();
        }
        this.testCaseRunner = testCaseRunner;
        this.injector = injector;
    }

    @Override
    public void run() {
        injector.injectInstance();
        testCaseRunner.run();
    }
}