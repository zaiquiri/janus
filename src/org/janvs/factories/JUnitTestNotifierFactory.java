package org.janvs.factories;

import org.janvs.junit.helpers.notifiers.JunitTestNotifier;
import org.janvs.specs.TestCase;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

public class JUnitTestNotifierFactory implements TestNotifierFactory {
    private final RunNotifier notifier;

    public JUnitTestNotifierFactory(final RunNotifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public TestNotifier createNotifier(final Object instance, final TestCase test) {
        final Description testDescription = Description.createTestDescription(instance.getClass(), test.getName());
        return new JunitTestNotifier(notifier, testDescription);
    }
}
