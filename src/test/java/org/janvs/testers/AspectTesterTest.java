package org.janvs.testers;

import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AspectTesterTest {

    @Mock
    private TestCase testCase;
    @Mock
    private Object testClassInstance;
    @Mock
    private TestNotifier testNotifier;

    private AspectTester aspectTester;


    @Before
    public void setup(){
        aspectTester = new AspectTester(testCase, testClassInstance, testNotifier);
    }

    @Test
    public void shouldCallFireTestStartedAndInvokeTestOnTestClassInstanceWhenTestIsRun() throws InvocationTargetException, IllegalAccessException {
        aspectTester.run();

        verify(testNotifier).testStarted();
        verify(testCase).invoke(testClassInstance);
    }

    @Test
    public void shouldFailTestIfTestExpectedExceptionAndNoneWereThrown() {
        when(testCase.shouldThrowException()).thenReturn(true);

        aspectTester.run();

        verify(testNotifier).testFailed(any(Exception.class));
    }

    @Test
    public void shouldCallTestFinishedIfTestWasNotExpectingAnyExceptionAndDidNotThrowAny() {
        when(testCase.shouldThrowException()).thenReturn(false);

        aspectTester.run();

        verify(testNotifier).testFinished();
    }

    @Test
    public void shouldCallTestFinishedWhenTestThrewExpectedException() throws InvocationTargetException, IllegalAccessException {
        final InvocationTargetException invocationTargetException = new InvocationTargetException(new NullPointerException());
        when(testCase.expectsException(NullPointerException.class)).thenReturn(true);
        doThrow(invocationTargetException).when(testCase).invoke(testClassInstance);

        aspectTester.run();

        verify(testNotifier).testFinished();
    }

    @Test
    public void shouldCallFailTestWhenTestThrowsUnexpectedException() throws InvocationTargetException, IllegalAccessException {
        final InvocationTargetException invocationTargetException = new InvocationTargetException(new NullPointerException());
        when(testCase.expectsException(NullPointerException.class)).thenReturn(false);
        doThrow(invocationTargetException).when(testCase).invoke(testClassInstance);

        aspectTester.run();

        verify(testNotifier).testFailed(invocationTargetException);
    }

    @Test
    public void shouldCallFailTestWhenTestExpectsAnExceptionYetThrowsADifferentOne() throws InvocationTargetException, IllegalAccessException {
        final InvocationTargetException invocationTargetException = new InvocationTargetException(new RuntimeException());
        when(testCase.expectsException(NullPointerException.class)).thenReturn(true);
        doThrow(invocationTargetException).when(testCase).invoke(testClassInstance);

        aspectTester.run();

        verify(testNotifier).testFailed(invocationTargetException);
    }

    @Test
    public void shouldCallFailTestWhenTestThrowsAnExceptionThatIsNotAnInvocationTargetException() throws InvocationTargetException, IllegalAccessException {
        final Exception generalException = new RuntimeException();
        doThrow(generalException).when(testCase).invoke(testClassInstance);

        aspectTester.run();

        verify(testNotifier).testFailed(generalException);
    }
}
