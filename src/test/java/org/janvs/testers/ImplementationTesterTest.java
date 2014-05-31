package org.janvs.testers;

import org.janvs.factories.InstanceTesterFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImplementationTesterTest {

    @Mock
    private InstanceTesterFactory instanceTesterFactory;

    @Test
    public void shouldNotDoAnythingWhenInstancesIsEmpty() {
        new ImplementationTester(new ArrayList<>(), instanceTesterFactory).run();

        verify(instanceTesterFactory, never()).createFor(any(Object.class));
    }

    @Test
    public void shouldCallMakeAndRunOnceForOneInstance() {
        final ArrayList<Object> instances = new ArrayList<>();
        final Object instanceOne = new Object();
        instances.add(instanceOne);

        final Tester tester  = mock(Tester.class);
        when(instanceTesterFactory.createFor(instanceOne)).thenReturn(tester);

        new ImplementationTester(instances, instanceTesterFactory).run();

        verify(instanceTesterFactory, times(1)).createFor(instanceOne);
        verify(tester, times(1)).run();
    }

    @Test
    public void shouldCallMakeAndRunThreeTimesForEachThreeInstances() {
        final ArrayList<Object> instances = new ArrayList<>();
        final Object instanceOne = new Object();
        final Object instanceTwo = new Object();
        final Object instanceThree = new Object();
        instances.add(instanceOne);
        instances.add(instanceTwo);
        instances.add(instanceThree);
        final Tester testerOne  = mock(Tester.class);
        when(instanceTesterFactory.createFor(instanceOne)).thenReturn(testerOne);
        final Tester testerTwo  = mock(Tester.class);
        when(instanceTesterFactory.createFor(instanceTwo)).thenReturn(testerTwo);
        final Tester testerThree  = mock(Tester.class);
        when(instanceTesterFactory.createFor(instanceThree)).thenReturn(testerThree);

        new ImplementationTester(instances, instanceTesterFactory).run();

        verify(instanceTesterFactory, times(1)).createFor(instanceOne);
        verify(testerOne, times(1)).run();
        verify(instanceTesterFactory, times(1)).createFor(instanceTwo);
        verify(testerTwo, times(1)).run();
        verify(instanceTesterFactory, times(1)).createFor(instanceThree);
        verify(testerThree, times(1)).run();
    }

}
