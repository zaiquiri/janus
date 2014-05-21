package com.zaiquiri.janus;

public class InjectingTestCaseRunnerFactory implements TestCaseRunnerFactory {
    private final Object testClassInstance;
    private final TestNotifierFactory testNotifierFactory;
    private final Injector injector;

    public InjectingTestCaseRunnerFactory(final Object testClassInstance, final TestNotifierFactory testNotifierFactory, final Injector injector) {
        this.testClassInstance = testClassInstance;
        this.testNotifierFactory = testNotifierFactory;
        this.injector = injector;
    }

    @Override
    public Runnable createRunnerFor(final TestCase test) {
        final Runnable testCaseRunner = new TestCaseRunner(test, testClassInstance, testNotifierFactory.createNotifier(test));
        return new InjectingTestCaseRunner(testCaseRunner, injector);
    }
}
