package com.zaiquiri.janus;

public class InjectingAspectTesterFactory implements AspectTesterFactory {
    private final Object testClassInstance;
    private final TestNotifierFactory testNotifierFactory;
    private final Injector injector;
    private final Object implementationOfInterface;

    public InjectingAspectTesterFactory(final Object testClassInstance, final TestNotifierFactory testNotifierFactory, final Injector injector, final Object implementationOfInterface) {
        this.testClassInstance = testClassInstance;
        this.testNotifierFactory = testNotifierFactory;
        this.injector = injector;
        this.implementationOfInterface = implementationOfInterface;
    }

    @Override
    public Tester createFor(final TestCase test) {
        final TestNotifier notifier = testNotifierFactory.createNotifier(implementationOfInterface, test);
        final Tester aspectTester = new AspectTester(test, testClassInstance, notifier);
        return new InjectingAspectTester(aspectTester, injector);
    }
}
