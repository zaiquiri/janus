package org.janvs.instancemakers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.janvs.util.Combinator.getAllCombosOf;

public class ConstructorInstanceMaker implements InstanceMaker {

    @Override
    public List<Object> createInstancesOf(final Class<?> implementor) {
        final List<Object> instances = new ArrayList<>();
        for (final Constructor constructor : implementor.getConstructors()) {
            instances.addAll(allInstancesFrom(constructor));
        }
        return instances;
    }

    private Collection<Object> allInstancesFrom(final Constructor constructor) {
        final List<Object> instances = new ArrayList<>();

        final Collection allParameterCombinations = getAllCombosOf(constructor.getParameterTypes());

        for (Object combo : allParameterCombinations) {
            instances.add(makeInstanceWith(constructor, (List) combo));
        }
        return instances;
    }

    private Object makeInstanceWith(final Constructor constructor, final List parameters) {
        try {
            return constructor.newInstance(parameters.toArray());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}