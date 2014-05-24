package org.janvs.instantiators;

import java.lang.reflect.Constructor;
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
                    addMockFor(parameter, combo);
                } else {
                    addNullFor(parameter, combo);
                }
            }
            allCombos.add(combo);
        }
        return allCombos;
    }

    private void addNullFor(final Class<?> parameter, final ArrayList<Object> combo) {
        if (parameter.isPrimitive()) {
            combo.add(primitive(parameter));
        } else {
            combo.add(null);
        }
    }

    private void addMockFor(final Class<?> parameter, final ArrayList<Object> combo) {
        if (parameter.isPrimitive()) {
            combo.add(primitive(parameter));
        } else if (isFinalClass(parameter)) {
            combo.add(finalClass(parameter));
        } else {
            combo.add(mock(parameter));
        }
    }

    private Object finalClass(final Class<?> clazz) {
        try {
            if (clazz.getConstructors().length > 0) {
                final Object constructorInstance = createClassFromConstructor(clazz);
                if (constructorInstance != null)
                    return constructorInstance;
                if (hasAFactoryConstructor(clazz)) {
                    final Object factoryInstance = createClassFromFactory(clazz);
                    if (factoryInstance != null)
                        return factoryInstance;
                }
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return clazz.cast(null);
    }

    private Object createClassFromFactory(final Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        Method constructor = null;
        for (Method method : clazz.getMethods()) {
            if (method.getReturnType() == clazz)
                constructor = method;
        }
        final int numberOfParams = constructor.getParameterTypes().length;
        try {
            return constructor.invoke(clazz, new Object[numberOfParams]);
        } catch (Exception e) {}
        return null;
    }

    private boolean hasAFactoryConstructor(final Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getReturnType() == clazz)
                return true;
        }
        return false;
    }

    private Object createClassFromConstructor(final Class<?> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        final Constructor<?> constructor = clazz.getConstructors()[0];
        final int numberOfParams = constructor.getParameterTypes().length;
        if (numberOfParams == 0) {
            return constructor.newInstance();
        }
        final ArrayList<Object> params = new ArrayList<>();
        for (Class param : constructor.getParameterTypes()) {
            addNullFor(param, params);
        }
        try {
            return constructor.newInstance(params.toArray());
        } catch (Exception e) {
        }
        return null;
    }

    private Object primitive(final Class<?> parameter) {
        if (byte.class == parameter) {
            return Byte.valueOf((byte) 0);
        } else if (short.class == parameter) {
            return Short.valueOf((short) 0);
        } else if (int.class == parameter) {
            return Integer.valueOf(0);
        } else if (long.class == parameter) {
            return Long.valueOf(0);
        } else if (float.class == parameter) {
            return Float.valueOf(0);
        } else if (double.class == parameter) {
            return Double.valueOf(0);
        } else if (boolean.class == parameter) {
            return Boolean.valueOf(false);
        } else if (char.class == parameter) {
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
