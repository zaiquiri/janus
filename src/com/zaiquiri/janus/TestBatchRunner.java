package com.zaiquiri.janus;

public class TestBatchRunner implements Runnable {
    private final InstanceTestRunnerFactory instanceTestRunnerFactory;
    private final Iterable<Object> instances;

    public TestBatchRunner(final Iterable<Object> instances, final InstanceTestRunnerFactory instanceTestRunnerFactory) {
        this.instanceTestRunnerFactory = instanceTestRunnerFactory;
        this.instances = instances;
    }

    @Override
    public void run() {
        for (final Object instance : instances) {
            //beforeall
            runThe(instance);
            //afterall
        }
    }

    private void runThe(final Object instance) {
        getRunnerFor(instance).run();
    }

    private Runnable getRunnerFor(final Object instance) {
        return instanceTestRunnerFactory.createRunnerFor(instance);
    }
}
