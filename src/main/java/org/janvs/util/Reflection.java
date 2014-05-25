package org.janvs.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    public static List<Method> getFactoryMethodsOf(final Class<?> clazz) {
        final List<Method> factories = new ArrayList<>();
        for (Method method : clazz.getMethods()) {
            if (isAFactory(method)) {
                factories.add(method);
            }
        }
        return factories;
    }

    static private boolean isAFactory(final Method method) {
        final Class<?> declaringClass = method.getDeclaringClass();
        final Class<?> returnType = method.getReturnType();
        return (declaringClass == returnType);
    }

    public static int numberOfParamsFor(final Method constructor) {
        return constructor.getParameterTypes().length;
    }

    public static Method getAFactoryMethodIn(final Class<?> clazz) {
        Method constructor = null;
        for (Method method : clazz.getMethods()) {
            if (method.getReturnType() == clazz)
                constructor = method;
        }
        return constructor;
    }

    public static boolean thereAreConstructorsIn(final Class<?> clazz) {
        return clazz.getConstructors().length > 0;
    }

    public static boolean isFinalClass(final Class<?> parameter) {
        return Modifier.isFinal(parameter.getModifiers());
    }

    public static boolean thereAreFactoryConstructorsIn(final Class<?> clazz) {
        for (Method method : clazz.getMethods()) {
            if (method.getReturnType() == clazz)
                return true;
        }
        return false;
    }

}
