package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class JunitTestNotifier implements TestNotifier {
    private final Description description;
    private final RunNotifier notifier;

    public JunitTestNotifier(final RunNotifier notifier, final Description description) {
        this.notifier = notifier;
        this.description = description;
    }

    @Override
    public void testStarted() {
        notifier.fireTestStarted(description);
    }

    @Override
    public void testFailed(final Exception e) {
        notifier.fireTestFailure(new Failure(description, e));
    }

    @Override
    public void testSucceeded() {
        notifier.fireTestFinished(description);
    }
}
