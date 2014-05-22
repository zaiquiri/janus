package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class Janus extends Runner {
    private JanusEngine janusEngine;

    public Janus(final Class<?> testContainer) throws InstantiationException, IllegalAccessException {
        this.janusEngine = new JanusEngine(new TestContainerReader(testContainer).getTestClassData());
    }

    @Override
    public void run(final RunNotifier notifier) {
        TestNotifierFactory testNotifierFactory = new JUnitTestNotifierFactory(notifier);
        janusEngine.runWith(testNotifierFactory);
    }

    @Override
    public Description getDescription() {
        final String name = janusEngine.getName();
        return Description.createSuiteDescription(name);
    }


}