package com.zaiquiri.janus;

import java.util.ArrayList;
import java.util.Collection;

public class InstanceMaker {
    private final InstanceMakerStrategy[] stratagies;

    public InstanceMaker(final InstanceMakerStrategy... stratagies) {
        this.stratagies = stratagies;
    }

    public Iterable<Object> getInstancesOf(final Class<?> implementor) {
        final Collection<Object> instances = new ArrayList<Object>();
        for (final InstanceMakerStrategy strategy : stratagies) {
            instances.addAll(strategy.createInstancesOf(implementor));
        }
        return instances;
    }
}
