package org.janvs.injectors;

import java.lang.reflect.Field;

public class FieldInjector implements Injector {


    private final Field fieldToBeSet;
    private final Object instanceWithField;
    private final Object valueForField;

    public FieldInjector(final Field fieldToBeSet, final Object instanceWithField, final Object valueForField) {
        this.fieldToBeSet = fieldToBeSet;
        this.instanceWithField = instanceWithField;
        this.valueForField = valueForField;
    }

    @Override
    public void injectInstance() {
        fieldToBeSet.setAccessible(true);
        try {
            fieldToBeSet.set(instanceWithField, valueForField);
        } catch (final IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
