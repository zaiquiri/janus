package com.zaiquiri.janus;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class Janus extends Runner {
    private TheOracle theOracle;

    public Janus(final Class<?> testContainer) throws InstantiationException, IllegalAccessException {
        this.theOracle = new TheOracle(new TestContainerReader(testContainer).getTestClassData());
    }

    @Override
    public void run(final RunNotifier here) {
        theOracle.prayToJanus(andGiveUsASign(here));
    }

    @Override
    public Description getDescription() {
        final String name = theOracle.getName();
        return Description.createSuiteDescription(name);
    }

    private TestNotifierFactory andGiveUsASign(final RunNotifier here) {
        return new JUnitTestNotifierFactory(here);
    }

}