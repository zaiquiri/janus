package com.zaiquiri.janus;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleTestRunner implements Tester {
    private final TestNotifier testNotifier;
    private final Method testMethod;
    private final Object testClassInstance;

    public SingleTestRunner(final Method testMethod, final Object testClassInstance, final TestNotifier testNotifier) {
        this.testNotifier = testNotifier;
        this.testMethod = testMethod;
        this.testClassInstance = testClassInstance;
    }

    @Override
    public void test() {
        testStarted();
        final Class<? extends Throwable> expectedException = testMethod.getAnnotation(Test.class).expected();
        try {
            runTest();
            if (isNotExpected(expectedException)) {
                testSucceeded();
            } else {
                throw new Exception();
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

    private Object runTest() throws IllegalAccessException,
            InvocationTargetException {
        return testMethod.invoke(testClassInstance, (Object[]) null);
    }

    private boolean isNotExpected(final Class<? extends Throwable> expected) {
        return expected == org.junit.Test.None.class;
    }

}
