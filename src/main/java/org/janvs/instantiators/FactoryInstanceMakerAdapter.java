package org.janvs.instantiators;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

public class FactoryInstanceMakerAdapter implements InstanceMakerAdapter<Method> {
    @Override
    public Method[] getAll(final Class<?> implementor) {
        final Collection<Method> factories = new ArrayList<>();
        final Method[] methods = implementor.getMethods();
        for (final Method method : methods) {
            final Class<?> returnType = method.getReturnType();
            if (returnType.isAssignableFrom(implementor)) {
                factories.add(method);
            }
        }
        return factories.toArray(new Method[factories.size()]);
    }

    @Override
    public Class[] getParameterTypes(final Method method) {
        return method.getParameterTypes();
    }

    @Override
    public Object newInstance(final Method method, final Object... parameters) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return method.invoke(null, parameters);
    }
}
