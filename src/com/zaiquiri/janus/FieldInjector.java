package com.zaiquiri.janus;

import java.lang.reflect.Field;

public class FieldInjector implements Injector {


    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final Object instance;

    public FieldInjector(Field interfaceUnderTest, Object testClassInstance, Object instance) {

        this.interfaceUnderTest = interfaceUnderTest;
        this.testClassInstance = testClassInstance;
        this.instance = instance;
    }

    @Override
    public void injectInstance() {
        interfaceUnderTest.setAccessible(true);
        try {
            interfaceUnderTest.set(testClassInstance, instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
