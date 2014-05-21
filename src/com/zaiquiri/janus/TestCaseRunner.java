package com.zaiquiri.janus;

import java.lang.reflect.InvocationTargetException;

public class TestCaseRunner implements Runnable {
    private final TestNotifier testNotifier;
    private final TestCase testCase;
    private final Object testClassInstance;

    public TestCaseRunner(final TestCase testCase, final Object testClassInstance, final TestNotifier testNotifier) {
        this.testNotifier = testNotifier;
        this.testCase = testCase;
        this.testClassInstance = testClassInstance;
    }

    @Override
    public void run() {
        testStarted();
        final Class<? extends Throwable> expectedException = testCase.expectedException();
        try {
            runTest();
            if (testCase.shouldThrowException()) {
                testFailed(new Exception("Expected exception " + testCase.expectedException().getName() + " not thrown"));
            } else {
                testSucceeded();
            }
        } catch (final InvocationTargetException e) {
            if (theExpectedHappened(expectedException, e)) {
                testSucceeded();
            } else {
                testFailed(e);
            }
        } catch (final Exception e) {
            testFailed(e);
        }
    }

    private void testStarted() {
        testNotifier.testStarted();
    }

    private void testFailed(final Exception e) {
        testNotifier.testFailed(e);
    }

    private boolean theExpectedHappened(
            Class<? extends Throwable> expectedException,
            InvocationTargetException e) {
        return expectedException == e.getTargetException().getClass();
    }

    private void testSucceeded() {
        testNotifier.testSucceeded();
    }

    private void runTest() throws IllegalAccessException,
            InvocationTargetException {
        testCase.invoke(testClassInstance);
    }
}
