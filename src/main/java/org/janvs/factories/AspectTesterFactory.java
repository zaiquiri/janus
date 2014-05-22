package org.janvs.factories;

import org.janvs.injectors.Injector;
import org.janvs.specs.TestCase;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.testers.AspectTester;
import org.janvs.testers.InjectingAspectTester;
import org.janvs.testers.Tester;

public class AspectTesterFactory implements Factory<Tester, TestCase> {
    private final Object testClassInstance;
    private final Factory<TestNotifier, TestCase> testNotifierFactory;
    private final Injector injector;

    public AspectTesterFactory(final Object testClassInstance, final Factory<TestNotifier, TestCase> testNotifierFactory, final Injector injector) {
        this.testClassInstance = testClassInstance;
        this.testNotifierFactory = testNotifierFactory;
        this.injector = injector;
    }

    @Override
    public Tester createFor(final TestCase test) {
        return new InjectingAspectTester(aspectTester(test), injector);
    }

    private AspectTester aspectTester(final TestCase test) {
        return new AspectTester(test, testClassInstance, notifier(test));
    }

    private TestNotifier notifier(final TestCase test) {
        return testNotifierFactory.createFor(test);
    }
}
