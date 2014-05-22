package org.janvs.factories;

import org.janvs.junit.helpers.notifiers.JunitTestNotifier;
import org.janvs.specs.TestCase;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

public class JUnitTestNotifierFactory implements Factory<TestNotifier, TestCase> {
    private final RunNotifier notifier;
    private final Object instance;

    public JUnitTestNotifierFactory(final RunNotifier notifier, final Object instance) {
        this.notifier = notifier;
        this.instance = instance;
    }

    @Override
    public TestNotifier createFor(final TestCase test) {
        return new JunitTestNotifier(notifier, description(test));
    }

    private Description description(final TestCase test) {
        return Description.createTestDescription(instance.getClass(), test.getName());
    }
}
