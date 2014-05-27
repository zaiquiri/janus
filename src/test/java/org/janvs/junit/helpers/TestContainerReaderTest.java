package org.janvs.junit.helpers;

import org.janvs.annotations.JanusOptions;
import org.janvs.annotations.UnderTest;
import org.janvs.specs.TestCase;
import org.janvs.specs.TestClassData;
import org.janvs.specs.TestSuite;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class TestContainerReaderTest {

    @Test (expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenMoreThanOneFieldSpecifiedAsUnderTest() throws InstantiationException, IllegalAccessException {

        final TestContainerReader testContainerReader = new TestContainerReader(MoreThanOneFieldUnderTest.class);

        testContainerReader.getTestClassData();
    }

    @Test (expected = RuntimeException.class)
    public void shouldThrowRuntimeExpectionWhenNoFieldsSpecifiedAsUnderTest() throws InstantiationException, IllegalAccessException {

        final TestContainerReader testContainerReader = new TestContainerReader(NoFieldsUnderTest.class);

        testContainerReader.getTestClassData();
    }

    @Test
    public void shouldGetTheCorrectFieldUnderTest() throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        final TestContainerReader testContainerReader = new TestContainerReader(ProperTestClass.class);

        final Field fieldFromReader = testContainerReader.getTestClassData().interfaceUnderTest();
        final Field markedField = ProperTestClass.class.getField("undertest");

        assertThat(fieldFromReader, is(markedField));
    }

    @Test (expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenNoBasePackageSpecified() throws InstantiationException, IllegalAccessException {
        final TestContainerReader testContainerReader = new TestContainerReader(NoBasePackage.class);

        testContainerReader.getTestClassData();
    }

    @Test
    public void shouldGetTheCorrectBasePackageFromTheTestClass() throws InstantiationException, IllegalAccessException {
        final TestContainerReader testContainerReader = new TestContainerReader(ProperTestClass.class);

        final String basePackageFromReader = testContainerReader.getTestClassData().basePackage();

        assertThat(basePackageFromReader, is("org.myPackage"));
    }

    @Test
    public void shouldGetCorrectTestSuiteWhenThereAreTests() throws InstantiationException, IllegalAccessException, NoSuchMethodException {
        final TestContainerReader testContainerReader = new TestContainerReader(ProperTestClass.class);

        final TestSuite testSuite = testContainerReader.getTestClassData().testSuite();

        for (TestCase testCase : testSuite) {
            assertNotNull(ProperTestClass.class.getMethod(testCase.getName()));
        }
    }

    @Test
    public void shouldMakeTestClassDataWithCorrectTestContainerInstance() throws InstantiationException, IllegalAccessException {
        final TestContainerReader testContainerReader = new TestContainerReader(ProperTestClass.class);

        final TestClassData testClassData = testContainerReader.getTestClassData();

        assertTrue(ProperTestClass.class.isInstance(testClassData.testClass()));
    }

    @JanusOptions(basePackage = "org.myPackage")
    static class MoreThanOneFieldUnderTest {
        @UnderTest
        private String a;
        @UnderTest
        private String b;
    }

    @JanusOptions(basePackage = "org.myPackage")
    static class NoFieldsUnderTest {

    }

    @JanusOptions(basePackage = "org.myPackage")
    static class ProperTestClass {

        @UnderTest
        public String undertest;

        @Test
        public void testTrue() {
            assertTrue(true);
        }

        @Test
        public void testFalse() {
            assertFalse(false);
        }
    }

    static class NoBasePackage {
        @UnderTest
        private String undertest;
    }
}
