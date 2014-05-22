package org.janvs.instantiators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConstructorInstanceMaker implements InstanceMaker {

    @Override
    public Collection<Object> createInstancesOf(final Class<?> implementor) {
        final List<Object> instances = new ArrayList<Object>();
        for (final java.lang.reflect.Constructor constructor : implementor.getConstructors()) {
            instances.addAll(allInstancesFrom(constructor));
        }
        return instances;
    }

    private Collection<Object> allInstancesFrom(final Constructor constructor) {
        final List<Object> instances = new ArrayList<Object>();
        try {
            final Class[] parameterTypes = constructor.getParameterTypes();
            final Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];
                if (parameterType.isPrimitive()) {
                    parameters[i] = 0;
                }
            }
            instances.add(constructor.newInstance(parameters));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }
}