package org.janvs.instantiators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;

public class FactoryInstanceMaker implements InstanceMaker {

    public static final String PRIMITIVE = "primitive";

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
        for (Object params : allParameterCombinations) {
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
        final int numberOfParamsNeeded = parameters.length;
        final double numberOfCombos = Math.pow(2, numberOfParamsNeeded);

        for (int comboNumber = 0; comboNumber < numberOfCombos; comboNumber++) {
            boolean[] booleans = getBooleanArrayFor(comboNumber, numberOfParamsNeeded);
            final ArrayList<Object> combo = new ArrayList<>();

            while (combo.size() < numberOfParamsNeeded) {
                final Class<?> parameter = parameters[combo.size()];
                if (booleans[combo.size()]) {
                    if (parameter.isPrimitive()) {
                        combo.add(primitive(parameter));
                    } else if (isFinalClass(parameter)) {
                        combo.add(null); //cannot mock
                    } else {
                        combo.add(mock(parameter));
                    }
                } else {
                    if (parameter.isPrimitive()) {
                        combo.add(primitive(parameter));
                    } else {
                        combo.add(null);
                    }
                }
            }
            allCombos.add(combo);
        }
        return allCombos;
    }

    private Object primitive(final Class<?> parameter) {
        if (byte.class == parameter){
            return Byte.valueOf((byte) 0);
        } else if (short.class == parameter){
            return Short.valueOf((short) 0);
        } else if (int.class == parameter){
            return Integer.valueOf(0);
        } else if (long.class == parameter){
            return Long.valueOf(0);
        } else if (float.class == parameter){
            return Float.valueOf(0);
        } else if (double.class == parameter){
            return Double.valueOf(0);
        } else if (boolean.class == parameter){
            return Boolean.valueOf(false);
        } else if (char.class == parameter){
            return Character.valueOf((char) 0);
        }
        return null;
    }

    private boolean isFinalClass(final Class<?> parameter) {
        return Modifier.isFinal(parameter.getModifiers());
    }

    private boolean[] getBooleanArrayFor(final int value, final int numberOfPlaces) {
        String binary = Integer.toBinaryString(value);
        if (binary.length() < numberOfPlaces) {
            binary = padWithZeros(binary, numberOfPlaces);
        }
        final char[] binaryArray = binary.toCharArray();
        final boolean[] booleans = new boolean[binaryArray.length];
        for (int i = 0; i < binary.length(); i++)
            booleans[i] = binaryArray[i] == '1' ? true : false;
        return booleans;
    }

    private String padWithZeros(String binary, final double numberOfPlaces) {
        for (int i = 0; i < numberOfPlaces - binary.length(); i++) {
            binary = "0" + binary;
        }
        return binary;
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
