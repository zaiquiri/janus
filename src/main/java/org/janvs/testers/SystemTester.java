package org.janvs.testers;

import org.janvs.factories.ImplementationTesterFactory;
import org.janvs.instantiators.InstanceMaker;

import java.util.Collection;

public class SystemTester implements Tester {

    private final Collection<Class<?>> allImplementors;
    private final ImplementationTesterFactory implementationTesterFactory;
    private final InstanceMaker instanceMaker;

    public SystemTester(final Collection<Class<?>> allImplementors, final InstanceMaker instanceMaker, final ImplementationTesterFactory implementationTesterFactory) {
        this.allImplementors = allImplementors;
        this.implementationTesterFactory = implementationTesterFactory;
        this.instanceMaker = instanceMaker;
    }

    @Override
    public void run() {
        for (final Class<?> implementor : allImplementors) {
            final Iterable<Object> instances = instanceMaker.createInstancesOf(implementor);
            implementationTesterFactory.createFor(instances).run();
        }
    }

}