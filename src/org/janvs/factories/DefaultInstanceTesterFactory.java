package org.janvs.factories;

import org.janvs.injectors.FieldInjector;
import org.janvs.injectors.Injector;
import org.janvs.testers.InstanceTester;
import org.janvs.util.TestClassData;
import org.janvs.specs.TestSuite;
import org.janvs.testers.Tester;

import java.lang.reflect.Field;

public class DefaultInstanceTesterFactory implements InstanceTesterFactory {
    private final Field interfaceUnderTest;
    private final Object testClassInstance;
    private final TestNotifierFactory testNotifierFactory;
    private final TestSuite testSuite;

    public DefaultInstanceTesterFactory(final TestClassData testClassData, final TestNotifierFactory testNotifierFactory) {
        this.testClassInstance = testClassData.testClass();
        this.testSuite = testClassData.testSuite();
        this.interfaceUnderTest = testClassData.interfaceUnderTest();
        this.testNotifierFactory = testNotifierFactory;
    }

    @Override
    public Tester createFor(final Object implementationOfInterface) {
        final Injector injector
                = new FieldInjector(interfaceUnderTest, testClassInstance, implementationOfInterface);
        final AspectTesterFactory aspectTesterFactory
                = new InjectingAspectTesterFactory(testClassInstance, testNotifierFactory, injector, implementationOfInterface);

        return new InstanceTester(testSuite, aspectTesterFactory);
    }
}
