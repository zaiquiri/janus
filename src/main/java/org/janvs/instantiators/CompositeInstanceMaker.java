package org.janvs.instantiators;

import java.util.ArrayList;
import java.util.Collection;

public class CompositeInstanceMaker implements InstanceMaker {
    private final InstanceMaker[] strategies;

    public CompositeInstanceMaker(final InstanceMaker... strategies) {
        this.strategies = strategies;
    }

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final Collection<Object> instances = new ArrayList<Object>();
        for (final InstanceMaker strategy : strategies) {
            instances.addAll(strategy.createInstancesOf(implementor));
        }
        return instances;
    }
}
