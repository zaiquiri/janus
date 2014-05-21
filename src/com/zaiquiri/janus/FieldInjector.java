package com.zaiquiri.janus;

import java.lang.reflect.Field;

public class FieldInjector implements Injector {


    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final Object valueForField;

    public FieldInjector(Field field, Object instanceWithField, Object valueForField) {

        this.interfaceUnderTest = field;
        this.testClassInstance = instanceWithField;
        this.valueForField = valueForField;
    }

    @Override
    public void injectInstance() {
        interfaceUnderTest.setAccessible(true);
        try {
            interfaceUnderTest.set(testClassInstance, valueForField);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
