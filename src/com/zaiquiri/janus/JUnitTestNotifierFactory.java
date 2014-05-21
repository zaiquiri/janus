package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

import java.lang.reflect.Method;

public class JUnitTestNotifierFactory implements TestNotifierFactory {

    private final Object instance;
    private final RunNotifier notifier;

    public JUnitTestNotifierFactory(final Object instance, final RunNotifier notifier) {
        this.instance = instance;
        this.notifier = notifier;
    }

    @Override
    public TestNotifier createNotifier(final Method test) {
        final Description testDescription = Description.createTestDescription(instance.getClass(), test.getName());
        return new JunitTestNotifier(notifier, testDescription);
    }
}
