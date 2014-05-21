package com.zaiquiri.janus;

public class InstanceSuiteTester implements Tester {
    private final TesterFromInstanceFactory testerFactory;
    private final Iterable<Object> instances;

    public InstanceSuiteTester(final TesterFromInstanceFactory testerFactory, final Iterable<Object> instances) {
        this.testerFactory = testerFactory;
        this.instances = instances;
    }

    @Override
    public void test() {
        for (final Object instance : instances) {
            final Tester tester = testerFactory.createTester(instance);
            //beforeall
            tester.test();
            //afterall
        }
    }
}
