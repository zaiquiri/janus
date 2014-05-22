package org.janvs.junit.helpers;

import org.janvs.annotations.JanusOptions;
import org.janvs.annotations.UnderTest;
import org.janvs.specs.TestCase;
import org.janvs.specs.TestSuite;
import org.janvs.util.TestClassData;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public final class TestContainerReader {
    private final Class<?> testContainer;

    public TestContainerReader(final Class<?> testContainer) {
        this.testContainer = testContainer;
    }

    public String getBasePackageUnderTest() {
        for (final Annotation annotation : testContainer.getAnnotations()) {
            if (annotation.annotationType().equals(JanusOptions.class)) {
                final JanusOptions options = (JanusOptions) annotation;
                return options.basePackage();
            }
        }
        throw new RuntimeException("Base package not defined in @" + JanusOptions.class.getName() + " annotation");
    }

    public Field getInterfaceUnderTest() {
        final Field[] fields = testContainer.getDeclaredFields();
        Field fieldUnderTest = null;
        int numberOfFieldsUnderTest = 0;
        for (final Field field : fields) {
            if (isUnderTest(field)) {
                numberOfFieldsUnderTest++;
                fieldUnderTest = field;
            }
        }
        if (numberOfFieldsUnderTest == 1) {
            return fieldUnderTest;
        }
        if (numberOfFieldsUnderTest > 1) {
            throw new RuntimeException("Multiple fields designated as @" + UnderTest.class.getName());
        }
        throw new RuntimeException("No field designated as @" + UnderTest.class.getName());
    }

    public List<TestCase> getTestCases() {
        final ArrayList<TestCase> testCases = new ArrayList<TestCase>();
        for (final Method method : testContainer.getDeclaredMethods()) {
            if (isATestMethod(method)) {
                testCases.add(new TestCase(method));
            }
        }
        return testCases;
    }

    private boolean isUnderTest(Field field) {
        return field.getAnnotation(UnderTest.class) != null;
    }

    private boolean isATestMethod(final Method method) {
        return method.getAnnotation(Test.class) != null && method.getAnnotation(Ignore.class) == null;
    }

    public TestClassData getTestClassData() throws IllegalAccessException, InstantiationException {
        final List<TestCase> testCases = getTestCases();
        final Field interfaceUnderTest = getInterfaceUnderTest();
        final String basePackage = getBasePackageUnderTest();
        final TestSuite testSuite = new TestSuite(testCases);

        return new TestClassData(testContainer.newInstance(), testSuite, interfaceUnderTest, basePackage);

    }
}