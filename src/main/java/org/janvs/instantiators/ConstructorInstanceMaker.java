package org.janvs.instantiators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConstructorInstanceMaker implements InstanceMaker {

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final List<Object> instances = new ArrayList<>();
        for (final java.lang.reflect.Constructor constructor : implementor.getConstructors()) {
            instances.addAll(allInstancesFrom(constructor));
        }
        return instances;
    }

    private static Collection<Object> allInstancesFrom(final Constructor constructor) {
        final List<Object> instances = new ArrayList<>();
        try {
            final Class[] parameterTypes = constructor.getParameterTypes();
            final Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                final Class parameterType = parameterTypes[i];
                if (parameterType.isPrimitive()) {
                    parameters[i] = 0;
                }
            }
            instances.add(constructor.newInstance(parameters));
        } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }
}