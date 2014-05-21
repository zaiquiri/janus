package com.zaiquiri.janus;

public class TestSuiteForInstaces implements Tester {
    private final TesterFactory testerFactory;
    private final Iterable<Object> instances;

    public TestSuiteForInstaces(final Iterable<Object> instances, final TesterFactory testForInstanceFactory) {
        this.testerFactory = testForInstanceFactory;
        this.instances = instances;
    }

    @Override
    public void test() {
        for (final Object instance : instances) {
            //beforeall
            getTesterFor(instance).test();
            //afterall
        }
    }

    private Tester getTesterFor(final Object instance) {
        return testerFactory.createTester(instance);
    }
}
