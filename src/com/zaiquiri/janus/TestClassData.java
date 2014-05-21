package com.zaiquiri.janus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class TestClassData {
    private final Object testClass;
    private final List<Method> testMethods;
    private final Field interfaceUnderTest;

    public TestClassData(final Object testClass, final List<Method> testMethods, final Field interfaceUnderTest) {
        this.testClass = testClass;
        this.testMethods = testMethods;
        this.interfaceUnderTest = interfaceUnderTest;
    }

    public Object testClass() {
        return testClass;
    }

    public List<Method> testMethods() {
        return testMethods;
    }

    public Field interfaceUnderTest() {
        return interfaceUnderTest;
    }
}
