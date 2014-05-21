package com.zaiquiri.janus;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class TestClassData {
    private final Object testClass;
    private final List<TestCase> testCases;
    private final Field interfaceUnderTest;

    public TestClassData(final Object testClass, final List<TestCase> testCases, final Field interfaceUnderTest) {
        this.testClass = testClass;
        this.testCases = testCases;
        this.interfaceUnderTest = interfaceUnderTest;
    }

    public Object testClass() {
        return testClass;
    }

    public List<TestCase> testMethods() {
        return testCases;
    }

    public Field interfaceUnderTest() {
        return interfaceUnderTest;
    }
}
