package com.zaiquiri.janus;

public class TestSuiteForInstaces implements Tester {
    private final TestFactory testerFactory;
    private final Iterable<Object> instances;

    public TestSuiteForInstaces(final Iterable<Object> instances, final TestFactory testerFactory) {
        this.testerFactory = testerFactory;
        this.instances = instances;
    }

    @Override
    public void runAllTests() {
        for (final Object instance : instances) {
            final Tester tester = testerFactory.createTester(instance);
            //beforeall
            tester.runAllTests();
            //afterall
        }
    }
}
