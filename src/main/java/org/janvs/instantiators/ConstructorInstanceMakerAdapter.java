package org.janvs.instantiators;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorInstanceMakerAdapter implements InstanceMakerAdapter<Constructor> {

    @Override
    public Constructor[] getAll(final Class<?> implementor) {
        return implementor.getConstructors();
    }

    @Override
    public Class[] getParameterTypes(final Constructor constructor) {
        return constructor.getParameterTypes();
    }

    @Override
    public Object newInstance(final Constructor constructor, final Object... parameters) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return constructor.newInstance(parameters);
    }
}
