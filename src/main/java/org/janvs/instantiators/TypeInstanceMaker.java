package org.janvs.instantiators;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeInstanceMaker<T> implements  InstanceMaker {
    private final InstanceMakerAdapter<T> instanceMakerAdapter;

    public TypeInstanceMaker(final InstanceMakerAdapter<T> instanceMakerAdapter) {
        this.instanceMakerAdapter = instanceMakerAdapter;
    }

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
    final Collection<Object> instances = new ArrayList<>();
    for (final T type : getTypes(implementor)) {
        instances.addAll(allInstancesFrom(type));
    }
    return instances;
}

    private T[] getTypes(final Class<?> implementor) {
        return instanceMakerAdapter.getAll(implementor);
    }

    private Collection<Object> allInstancesFrom(final T type) {
        final List<Object> instances = new ArrayList<>();
        try {
            instances.add(instanceMakerAdapter.newInstance(type, getParameters(type)));
        } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }

    private Object[] getParameters(final T type) {
        final Class[] parameterTypes = instanceMakerAdapter.getParameterTypes(type);
        final Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            final Class parameterType = parameterTypes[i];
            if (parameterType.isPrimitive()) {
                parameters[i] = 0;
            }
        }
        return parameters;
    }
}
