package com.zaiquiri.janus;

import java.lang.reflect.Field;
import java.util.Collection;

public class JanusEngine {

    private final ClassFinder classFinder;
    private final TestSuiteForInstacesFactory testFactory;
    private final InstanceMaker instanceMaker;

    public JanusEngine(ClassFinder classFinder, InstanceMaker instanceMaker, TestSuiteForInstacesFactory testSuiteFactory) {
        this.classFinder = classFinder;
        this.testFactory = testSuiteFactory;
        this.instanceMaker = instanceMaker;
    }

    public void testAllPossibleImplementationsOf(Field interfaceUnderTest) {
        for (final Class<?> implementor : getAllClassesThatImplement(interfaceUnderTest)) {
            Iterable<Object> instances = instanceMaker.getInstancesOf(implementor);
            testFactory.createSuiteFor(instances).run();
        }
    }

    private Collection<Class<?>> getAllClassesThatImplement(final Field ourInterface) {
        return classFinder.findImplementationsOf(ourInterface.getType());
    }
}