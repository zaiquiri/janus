package org.janvs.instantiators;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeInstanceMaker implements InstanceMaker {
    private final InstanceMaker[] instanceMakers;

    public CompositeInstanceMaker(final InstanceMaker... instanceMakers) {
        this.instanceMakers = instanceMakers;
    }

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final Collection<Object> instances = new ArrayList<>();
        for (final InstanceMaker instanceMaker : instanceMakers) {
            instances.addAll(instanceMaker.createInstancesOf(implementor));
        }
        return instances;
    }
}
