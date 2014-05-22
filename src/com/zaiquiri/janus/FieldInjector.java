package com.zaiquiri.janus;

import java.lang.reflect.Field;

public class FieldInjector implements Injector {


    private final Field fieldToBeSet;
    private final Object instanceWithField;
    private final Object valueForField;

    public FieldInjector(Field fieldToBeSet, Object instanceWithField, Object valueForField) {
        this.fieldToBeSet = fieldToBeSet;
        this.instanceWithField = instanceWithField;
        this.valueForField = valueForField;
    }

    @Override
    public void injectInstance() {
        fieldToBeSet.setAccessible(true);
        try {
            fieldToBeSet.set(instanceWithField, valueForField);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
