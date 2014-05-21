package com.zaiquiri.janus;

import java.util.Collection;

public class TestSuiteRunner implements Runnable {
    private final Collection<TestCase> testSuite;
    private final TestCaseRunnerFactory testCaseRunnerFactory;

    public TestSuiteRunner(final Collection<TestCase> testCases, final TestCaseRunnerFactory testCaseRunnerFactory) {
        this.testSuite = testCases;
        this.testCaseRunnerFactory = testCaseRunnerFactory;
    }

    @Override
    public final void run() {
        for (final TestCase testCase : testSuite) {
            //before
            runThe(testCase);
            //after
        }
    }

    private void runThe(final TestCase testCase) {
        getRunnerFor(testCase).run();
    }

    private Runnable getRunnerFor(final TestCase testCase) {
        return testCaseRunnerFactory.createRunnerFor(testCase);
    }
}
