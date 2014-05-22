package org.janvs.factories;

import org.janvs.injectors.Injector;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;
import org.janvs.testers.Tester;

public class AspectTesterFactoryFactory implements Factory<Factory<Tester, TestCase>, Object> {
    private final Object testClass;
    private final Factory<Factory<TestNotifier, TestCase>, Object> testNotifierFactoryFactory;
    private final Factory<Injector, Object> injectorFactory;

    public AspectTesterFactoryFactory(final Object testClass, final Factory<Factory<TestNotifier, TestCase>, Object> testNotifierFactoryFactory, final Factory<Injector, Object> injectorFactory) {
        this.testClass = testClass;
        this.testNotifierFactoryFactory = testNotifierFactoryFactory;
        this.injectorFactory = injectorFactory;
    }

    @Override
    public Factory<Tester, TestCase> createFor(final Object implementationOfInterface) {
        return new AspectTesterFactory(testClass, testNotifierFactory(implementationOfInterface), injector(implementationOfInterface));
    }

    private Factory<TestNotifier, TestCase> testNotifierFactory(final Object implementationOfInterface) {
        return testNotifierFactoryFactory.createFor(implementationOfInterface);
    }

    private Injector injector(final Object implementationOfInterface) {
        return injectorFactory.createFor(implementationOfInterface);
    }
}
