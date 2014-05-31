package org.janvs.testers;

import org.janvs.factories.ImplementationTesterFactory;
import org.janvs.instancemakers.InstanceMaker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SystemTesterTest {

    @Mock
    private ImplementationTesterFactory implementationTesterFactory;
    @Mock
    private InstanceMaker instanceMaker;

    @Test
    public void shouldNotCallCreateInstancesWhenThereAreNoImplementors() {

        final Collection<Class<?>> implementors = new ArrayList<>();

        new SystemTester(implementors, instanceMaker, implementationTesterFactory).run();

        verify(instanceMaker, never()).createInstancesOf(any(Class.class));

    }

    @Test
    public void shouldCallCreateInstancesAndRunOnceWhenOneImplementor() {
        final Collection<Class<?>> implementors = new ArrayList<>();
        final Class implementor = Object.class;
        implementors.add(implementor);
        final Collection<Object> instances = new ArrayList<>();
        when(instanceMaker.createInstancesOf(implementor)).thenReturn(instances);
        Tester tester = mock(Tester.class);
        when(implementationTesterFactory.createFor(instances)).thenReturn(tester);

        new SystemTester(implementors, instanceMaker, implementationTesterFactory).run();

        verify(instanceMaker, times(1)).createInstancesOf(implementor);
        verify(implementationTesterFactory, times(1)).createFor(instances);
        verify(tester, times(1)).run();
    }

}
