package org.janvs.instantiators;

import java.lang.reflect.InvocationTargetException;

public interface InstanceMakerAdapter<T> {
    T[] getAll(final Class<?> implementor);
    Class[] getParameterTypes(T type);
    Object newInstance(T type, Object... parameters) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
