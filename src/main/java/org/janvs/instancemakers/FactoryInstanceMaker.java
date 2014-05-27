package org.janvs.instancemakers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.janvs.util.Combinator.getAllCombosOf;
import static org.janvs.util.Reflection.getFactoryMethodsOf;

public class FactoryInstanceMaker implements InstanceMaker {

    @Override
    public List<Object> createInstancesOf(final Class<?> clazz) {
        final ArrayList<Object> instances = new ArrayList<>();
        for (Method constructor : getFactoryMethodsOf(clazz)) {
            instances.addAll(allInstancesFrom(constructor));
        }
        return instances;
    }

    public List<Object> allInstancesFrom(final Method constructor) {
        final List<Object> instances = new ArrayList<>();
        constructor.setAccessible(true);
        final Collection allParameterCombinations = getAllCombosOf(constructor.getParameterTypes());
        for (Object params : allParameterCombinations) {
            instances.add(makeInstanceWith(constructor, (List) params));
        }
        return instances;
    }

    private Object makeInstanceWith(final Method constructor, List parameters) {
        try {
            return constructor.invoke(constructor.getDeclaringClass(), parameters.toArray());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }


}
