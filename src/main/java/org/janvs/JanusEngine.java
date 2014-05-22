package org.janvs;

import org.janvs.factories.DefaultImplementationTesterFactory;
import org.janvs.factories.DefaultInstanceTesterFactory;
import org.janvs.factories.TestNotifierFactory;
import org.janvs.instantiators.CompositeInstanceMaker;
import org.janvs.instantiators.ConstructorInstanceMaker;
import org.janvs.instantiators.FactoryInstanceMaker;
import org.janvs.instantiators.InstanceMaker;
import org.janvs.testers.SystemTester;
import org.janvs.util.ClassFinder;
import org.janvs.util.TestClassData;

import java.util.Collection;

public class JanusEngine {
    private final TestClassData testClassData;

    public JanusEngine(final TestClassData testClassData) {
        this.testClassData = testClassData;
    }

    public void runWith(final TestNotifierFactory notifierFactory) {
        new SystemTester(allImplementors(), InstanceMaker(), testerFactory(notifierFactory)).run();
    }

    private Collection<Class<?>> allImplementors() {
        return new ClassFinder(basePackage()).findImplementationsOf(interfaceUnderTest());
    }

    private InstanceMaker InstanceMaker() {
        return new CompositeInstanceMaker(new ConstructorInstanceMaker(), new FactoryInstanceMaker());
    }

    private DefaultImplementationTesterFactory testerFactory(final TestNotifierFactory testNotifierFactory) {
        return new DefaultImplementationTesterFactory(instanceTesterFactory(testNotifierFactory));
    }

    private DefaultInstanceTesterFactory instanceTesterFactory(final TestNotifierFactory testNotifierFactory) {
        return new DefaultInstanceTesterFactory(testClassData, testNotifierFactory);
    }

    private String basePackage() {
        return testClassData.basePackage();
    }

    private Class<?> interfaceUnderTest() {
        return testClassData.interfaceUnderTest().getType();
    }

    public String getName() {
        return testClassData.getName();
    }
}
