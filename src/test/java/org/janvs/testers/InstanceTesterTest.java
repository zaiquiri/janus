package org.janvs.testers;

import org.janvs.factories.AspectTesterFactory;
import org.janvs.specs.TestCase;
import org.janvs.specs.TestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InstanceTesterTest {

    @Mock
    private AspectTesterFactory aspectTesterFactory;

    @Test
    public void shouldNotDoAnythingWhenTestSuiteIsEmpty() {
        new InstanceTester(new TestSuite(new ArrayList<TestCase>()), aspectTesterFactory).run();

        verify(aspectTesterFactory, never()).createFor(any(TestCase.class));
    }

    @Test
    public void shouldCallMakeAndRunOnceForOneTestCase() {
        final ArrayList<TestCase> testCases = new ArrayList<>();
        final TestCase testCaseOne = mock(TestCase.class);
        testCases.add(testCaseOne);
        final TestSuite testSuite = new TestSuite(testCases);

        final Tester tester = mock(Tester.class);
        when(aspectTesterFactory.createFor(testCaseOne)).thenReturn(tester);

        new InstanceTester(testSuite, aspectTesterFactory).run();

        verify(aspectTesterFactory, times(1)).createFor(testCaseOne);
        verify(tester, times(1)).run();
    }

    @Test
    public void shouldCallMakeAndRunThreeTimesForEachThreeInstances() {
        final ArrayList<TestCase> testCases = new ArrayList<>();
        final TestCase testCaseOne = mock(TestCase.class);
        final TestCase testCaseTwo = mock(TestCase.class);
        final TestCase testCaseThree = mock(TestCase.class);
        testCases.add(testCaseOne);
        testCases.add(testCaseTwo);
        testCases.add(testCaseThree);
        final Tester testerOne = mock(Tester.class);
        when(aspectTesterFactory.createFor(testCaseOne)).thenReturn(testerOne);
        final Tester testerTwo = mock(Tester.class);
        when(aspectTesterFactory.createFor(testCaseTwo)).thenReturn(testerTwo);
        final Tester testerThree = mock(Tester.class);
        when(aspectTesterFactory.createFor(testCaseThree)).thenReturn(testerThree);

        new InstanceTester(new TestSuite(testCases), aspectTesterFactory).run();

        verify(aspectTesterFactory, times(1)).createFor(testCaseOne);
        verify(testerOne, times(1)).run();
        verify(aspectTesterFactory, times(1)).createFor(testCaseTwo);
        verify(testerTwo, times(1)).run();
        verify(aspectTesterFactory, times(1)).createFor(testCaseThree);
        verify(testerThree, times(1)).run();
    }
}
