package org.janvs.specs;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCase {
    private final Method method;

    public TestCase(final Method method) {
        this.method = method;
    }

    public String getName() {
        return method.getName();
    }

    public Class<? extends Throwable> expectedException() {
        return method.getAnnotation(Test.class).expected();
    }

    public void invoke(final Object testClassInstance) throws InvocationTargetException, IllegalAccessException {
        method.invoke(testClassInstance, (Object[]) null);
    }

    public boolean shouldThrowException() {
        return expectedException() != org.junit.Test.None.class;
    }


    public boolean expectsException(final Class<? extends Throwable> exception) {
        return expectedException() == exception;
    }
}
