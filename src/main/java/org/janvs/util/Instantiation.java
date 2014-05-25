package org.janvs.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.janvs.util.Reflection.*;
import static org.janvs.util.Reflection.numberOfParamsFor;
import static org.mockito.Mockito.mock;

public class Instantiation {
    public static Object nullFor(final Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return primitive(clazz);
        } else {
            return null;
        }
    }

    public static Object mockFor(final Class<?> clazz) {
        if (clazz.isPrimitive()) {
            return primitive(clazz);
        } else if (isFinalClass(clazz)) {
            return mockForFinalClass(clazz);
        } else {
            return mock(clazz);
        }
    }

    private static Object mockForFinalClass(final Class<?> clazz) {
        if (thereAreConstructorsIn(clazz)) {
            final Object constructorInstance = createClassFromConstructor(clazz);
            if (constructorInstance != null)
                return constructorInstance;
        }
        if (thereAreFactoryConstructorsIn(clazz)) {
            final Object factoryInstance = createClassFromFactory(clazz);
            if (factoryInstance != null)
                return factoryInstance;
        }
        return null;
    }

    private static Object createClassFromFactory(final Class<?> clazz) {
        Method constructor = getAFactoryMethodIn(clazz);
        final int numberOfParams = numberOfParamsFor(constructor);
        try {
            return constructor.invoke(clazz, new Object[numberOfParams]);
        } catch (Exception e) {
            return null;
        }
    }

    private static Object createClassFromConstructor(final Class<?> clazz) {
        final Constructor<?> constructor = clazz.getConstructors()[0];
        final int numberOfParams = constructor.getParameterTypes().length;
        try {
            if (numberOfParams == 0) {
                return constructor.newInstance();
            }
            final ArrayList<Object> params = createNullParamListFor(constructor);
            return constructor.newInstance(params.toArray());
        } catch (Exception e) {
            return null;
        }
    }

    private static ArrayList<Object> createNullParamListFor(final Constructor<?> constructor) {
        final ArrayList<Object> params = new ArrayList<>();
        for (Class param : constructor.getParameterTypes()) {
            nullFor(param);
        }
        return params;
    }

    private static Object primitive(final Class<?> parameter) {
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

}
