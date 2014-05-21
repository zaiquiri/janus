package com.zaiquiri.janus;

import java.lang.reflect.Field;
import java.util.Collection;

public class JanusTestEngine {

    private final String basePackage;
    private final TestFactory testFactory;

    public JanusTestEngine(String basePackage, TestFactory testFactory) {
        this.basePackage = basePackage;
        this.testFactory = testFactory;
    }

    void testAllPossibleImplementationsOf(Field interfaceUnderTest) {
        for (final Class<?> implementor : getAllClassesThatImplement(interfaceUnderTest)) {
            new ConstructorSuite().runSuiteForEveryConstructor(implementor, testFactory);
            new FactorySuite().runSuiteForEveryFactory(implementor, testFactory);
        }
    }

    private Collection<Class<?>> getAllClassesThatImplement(final Field ourInterface) {
        ClassFinder classFinder = new ClassFinder(basePackage);
        return classFinder.findImplementationsOf(ourInterface.getType());
    }
}