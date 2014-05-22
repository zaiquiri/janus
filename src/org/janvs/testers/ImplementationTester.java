package org.janvs.testers;

import org.janvs.factories.InstanceTesterFactory;

public class ImplementationTester implements Tester {
    private final InstanceTesterFactory instanceTesterFactory;
    private final Iterable<Object> instances;

    public ImplementationTester(final Iterable<Object> instances, final InstanceTesterFactory instanceTesterFactory) {
        this.instanceTesterFactory = instanceTesterFactory;
        this.instances = instances;
    }

    @Override
    public void run() {
        for (final Object instance : instances) {
            //beforeall
            runThe(instance);
            //afterall
        }
    }

    private void runThe(final Object instance) {
        getTesterFor(instance).run();
    }

    private Tester getTesterFor(final Object instance) {
        return instanceTesterFactory.createFor(instance);
    }
}
