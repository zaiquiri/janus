package com.zaiquiri.janus;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class FactorySuite {
    public FactorySuite() {
    }

    void runSuiteForEveryFactory(final Class<?> implementor, final TestFactory testerFactory) {
        for (final Method factory : getFactories(implementor)) {
            Tester tester = new TestSuiteForInstaces(createAllInstancesFromFactories(factory), testerFactory);
            tester.runAllTests();
        }
    }

    Iterable<Object> createAllInstancesFromFactories(final Method factory) {
        return new ArrayList<Object>();
    }

    Iterable<Method> getFactories(final Class<?> implementor) {
        return new ArrayList<Method>();
    }
}