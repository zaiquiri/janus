package com.zaiquiri.janus;

public class TestSuiteRunner implements Runnable {
    private final TestSuite testSuite;
    private final TestCaseRunnerFactory testCaseRunnerFactory;

    public TestSuiteRunner(final TestSuite testSuite, final TestCaseRunnerFactory testCaseRunnerFactory) {
        this.testSuite = testSuite;
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
