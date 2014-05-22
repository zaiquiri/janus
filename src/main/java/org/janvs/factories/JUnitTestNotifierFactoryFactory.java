package org.janvs.factories;

import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;
import org.junit.runner.notification.RunNotifier;

public class JUnitTestNotifierFactoryFactory implements Factory<Factory<TestNotifier, TestCase>, Object> {
    private final RunNotifier notifier;

    public JUnitTestNotifierFactoryFactory(final RunNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public Factory<TestNotifier, TestCase> createFor(final Object implementationOfInterface) {
        return new JUnitTestNotifierFactory(notifier, implementationOfInterface);
    }
}
