package org.janvs.junit;

import org.janvs.JanusEngine;
import org.janvs.factories.JUnitTestNotifierFactory;
import org.janvs.factories.TestNotifierFactory;
import org.janvs.junit.helpers.TestContainerReader;
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

    private TestNotifierFactory wrap(final RunNotifier notifier) {
        return new JUnitTestNotifierFactory(notifier);
    }

}