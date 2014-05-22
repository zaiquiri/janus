package org.janvs.junit;

import org.janvs.JanusEngine;
import org.janvs.factories.Factory;
import org.janvs.factories.JUnitTestNotifierFactoryFactory;
import org.janvs.junit.helpers.TestContainerReader;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;
import org.janvs.util.TestClassData;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class Janus extends Runner {
    private JanusEngine janusEngine;

    public Janus(final Class<?> testContainer) throws InstantiationException, IllegalAccessException {
        final TestClassData testClassData = new TestContainerReader(testContainer).getTestClassData();
        this.janusEngine = new JanusEngine(testClassData);
    }

    @Override
    public void run(final RunNotifier notifier) {
        janusEngine.runWith(wrap(notifier));
    }

    @Override
    public Description getDescription() {
        final String name = janusEngine.getName();
        return Description.createSuiteDescription(name);
    }

    private static Factory<Factory<TestNotifier, TestCase>, Object> wrap(final RunNotifier notifier) {
        return new JUnitTestNotifierFactoryFactory(notifier);
    }

}