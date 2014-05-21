package com.zaiquiri.janus;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ConstructorSuite {
    public void runSuiteForEveryConstructor(Class<?> implementor, TestFactory testFactory) {
        for (final Constructor constructor : implementor.getConstructors()) {
            Tester testSuite = new TestSuiteForInstaces(allInstancesFrom(constructor), testFactory);
            testSuite.runAllTests();
        }
    }

    private Iterable<Object> allInstancesFrom(Constructor constructor) {
        List<Object> instances = new ArrayList<Object>();
        try {
            final Class[] parameterTypes = constructor.getParameterTypes();
            final Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class parameterType = parameterTypes[i];
                if (parameterType.isPrimitive()) {
                    parameters[i] = 0;
                }
            }
            instances.add(constructor.newInstance(parameters));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instances;
    }
}