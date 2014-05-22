package org.janvs.util;

import org.janvs.specs.TestSuite;

import java.lang.reflect.Field;

public class TestClassData {
    private final Object testClass;
    private final TestSuite testSuite;
    private final Field interfaceUnderTest;
    private final String basePackage;

    public TestClassData(final Object testClass, final TestSuite testSuite, final Field interfaceUnderTest, final String basePackage) {
        this.testClass = testClass;
        this.testSuite = testSuite;
        this.interfaceUnderTest = interfaceUnderTest;
        this.basePackage = basePackage;
    }

    public Object testClass() {
        return testClass;
    }

    public TestSuite testSuite() {
        return testSuite;
    }

    public Field interfaceUnderTest() {
        return interfaceUnderTest;
    }

    public String basePackage() {
        return basePackage;
    }

    public String getName() {
        return testClass.getClass().getName();
    }
}
