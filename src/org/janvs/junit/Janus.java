package org.janvs.junit;

import org.janvs.TheOracle;
import org.janvs.factories.JUnitTestNotifierFactory;
import org.janvs.factories.TestNotifierFactory;
import org.janvs.junit.helpers.TestContainerReader;
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