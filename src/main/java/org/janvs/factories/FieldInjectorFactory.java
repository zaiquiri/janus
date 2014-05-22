package org.janvs.factories;

import org.janvs.injectors.FieldInjector;
import org.janvs.injectors.Injector;
import org.janvs.util.TestClassData;

public class FieldInjectorFactory implements Factory<Injector, Object> {
    private final TestClassData testClassData;

    public FieldInjectorFactory(final TestClassData testClassData) {
        this.testClassData = testClassData;
    }

    @Override
    public Injector createFor(final Object implementationOfInterface) {
        return new FieldInjector(testClassData.interfaceUnderTest(), testClassData.testClass(), implementationOfInterface);
    }
}
