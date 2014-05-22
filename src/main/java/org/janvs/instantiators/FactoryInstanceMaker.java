package org.janvs.instantiators;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FactoryInstanceMaker implements InstanceMaker {

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final List<Object> instances = new ArrayList<>();
        for (final Method factory : getFactoryMethods()) {
            instances.addAll(allInstancesFrom(factory));
        }
        return instances;
    }

    private static Method[] getFactoryMethods() {
        return new Method[0];
    }

    private static List<Method> allInstancesFrom(final Method factory) {
        return new ArrayList<>();
    }
}