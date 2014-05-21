package com.zaiquiri.janus;

public class TestSuiteForInstacesFactory {

    private final InstanceTestRunnerFactory instanceTestRunnerFactory;

    public TestSuiteForInstacesFactory(final InstanceTestRunnerFactory instanceTestRunnerFactory) {
        this.instanceTestRunnerFactory = instanceTestRunnerFactory;
    }

    public TestBatchRunner createSuiteFor(final Iterable<Object> instances){
        return new TestBatchRunner(instances, instanceTestRunnerFactory);
    }


}
