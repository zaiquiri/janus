package org.janvs.instantiators.strategies;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FactoryStrategy implements InstanceMakerStrategy{

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final List<Object> instances = new ArrayList<Object>();
        for (final Method factory : getFactoryMethods()) {
            instances.addAll(allInstancesFrom(factory));
        }
        return instances;
    }

    private Method[] getFactoryMethods() {
        return new Method[0];
    }

    private List<Method> allInstancesFrom(final Method factory) {
        return new ArrayList<Method>();
    }
}