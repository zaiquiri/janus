package org.janvs.instantiators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;

public class FactoryInstanceMaker implements InstanceMaker {

    @Override
    public List<Object> createInstancesOf(final Class<?> clazz) {
        final ArrayList<Object> instances = new ArrayList<>();
        for (Method constructor : getFactoryMethodsOf(clazz)) {
            instances.addAll(allInstancesFrom(constructor));
        }
        return instances;
    }

    private List<Method> getFactoryMethodsOf(final Class<?> clazz) {
        final List<Method> factories = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if (isAFactory(method)) {
                factories.add(method);
            }
        }
        return factories;
    }

    private boolean isAFactory(final Method method) {
        final Class<?> declaringClass = method.getDeclaringClass();
        final Class<?> returnType = method.getReturnType();
        return (declaringClass == returnType);
    }


    private List<Object> allInstancesFrom(final Method factory) {
        final List<Object> instances = new ArrayList<>();
        final Collection allParameterCombinations = getAllParameterCombinations(factory);
        for ( Object params: allParameterCombinations){
            instances.add(makeInstanceWith(factory, (List) params));
        }
        return instances;
    }

    private Collection getAllParameterCombinations(final Method factory) {
        final Class<?>[] parameterTypes = factory.getParameterTypes();
        return getAllCombos(parameterTypes);
    }

    private ArrayList getAllCombos(final Class<?>[] parameters) {
        final ArrayList<Object> allCombos = new ArrayList<>();

        final int numberOfParams = parameters.length;

        final double numberOfCombos = Math.pow(2, numberOfParams);
        for (int i = 0; i < numberOfCombos; i++) {
            char[] binary = toBinary(i, numberOfParams);

            final ArrayList<Object> combo = new ArrayList<>();
            while (combo.size() < numberOfParams) {
                if (binary[combo.size()] == '1') {
                    if (Modifier.isFinal(parameters[combo.size()].getModifiers())){
                        combo.add(null);
                    } else {
                        combo.add(mock(parameters[combo.size()]));
                    }
                } else {
                    combo.add(null);
                }
            }
            allCombos.add(combo);
        }
        return allCombos;
    }

    private char[] toBinary(final int value, final int numberOfPlaces) {
        String binary = Integer.toBinaryString(value);
        if (binary.length() < numberOfPlaces){
            binary = padWithZeros(binary, numberOfPlaces);
        }
        return binary.toCharArray();
    }

    private String padWithZeros(String binary, final double numberOfCombos) {
        for (int i = 0; i<numberOfCombos-binary.length(); i++){
            binary = "0" + binary;
        }
        return binary;
    }

    private Object popFirstOff(final ArrayList parameters) {
        final Object first = parameters.get(0);
        parameters.remove(first);
        return first;
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
