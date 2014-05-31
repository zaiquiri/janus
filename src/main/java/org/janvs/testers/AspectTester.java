package org.janvs.testers;

import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;

import java.lang.reflect.InvocationTargetException;

public class AspectTester implements Tester {
    private final TestNotifier testNotifier;
    private final TestCase testCase;
    private final Object testClassInstance;

    public AspectTester(final TestCase testCase, final Object testClassInstance, final TestNotifier testNotifier) {
        this.testNotifier = testNotifier;
        this.testCase = testCase;
        this.testClassInstance = testClassInstance;
    }

    @Override
    public void run() {
        testStarted();
        try {
            runTest();
            if (testShouldThrowException()) {
                testFailedWithMissingException();
            } else {
                testFinished();
            }
        } catch (final InvocationTargetException exception) {
            if (testExpected(exception)) {
                testFinished();
            } else {
                testFailedWith(exception);
            }
        } catch (final Exception exception) {
            testFailedWith(exception);
        }
    }

    private boolean testExpected(final InvocationTargetException exception) {
        return testCase.expectsException(exception.getTargetException().getClass());
    }

    private boolean testShouldThrowException() {
        return testCase.shouldThrowException();
    }

    private void testFailedWithMissingException() {
        testFailedWith(new Exception("Expected exception " + testCase.expectedException().getName() + " not thrown"));
    }

    private void testStarted() {
        testNotifier.testStarted();
    }

    private void testFailedWith(final Exception exception) {
        testNotifier.testFailed(exception);
    }

    private void testFinished() {
        testNotifier.testFinished();
    }

    private void runTest() throws IllegalAccessException,
            InvocationTargetException {
        testCase.invoke(testClassInstance);
    }
}
