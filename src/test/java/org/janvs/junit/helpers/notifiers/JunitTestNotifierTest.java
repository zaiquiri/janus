package org.janvs.junit.helpers.notifiers;

import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JunitTestNotifierTest {

    @Mock
    private RunNotifier notifier;
    @Mock
    private Description description;

    @Test
    public void shouldFireTestStartedWhenTestStartedCalled () {
        final JunitTestNotifier junitTestNotifier = new JunitTestNotifier(notifier, description);

        junitTestNotifier.testStarted();

        verify(notifier).fireTestStarted(description);

    }

    @Test
    public void shouldFireTestFailedWhenTestFailedCalled() {
        final JunitTestNotifier junitTestNotifier = new JunitTestNotifier(notifier, description);

        junitTestNotifier.testFailed(new Exception());

        verify(notifier).fireTestFailure(any(Failure.class));
    }

    @Test
    public void shouldFireTestFinishedWhenTestSucceededCalled() {
        final JunitTestNotifier junitTestNotifier = new JunitTestNotifier(notifier, description);

        junitTestNotifier.testFinished();

        verify(notifier).fireTestFinished(description);
    }
}
