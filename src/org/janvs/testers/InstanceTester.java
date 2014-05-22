package org.janvs.testers;

import org.janvs.factories.AspectTesterFactory;
import org.janvs.specs.TestCase;
import org.janvs.specs.TestSuite;

public class InstanceTester implements Tester {
    private final TestSuite testSuite;
    private final AspectTesterFactory aspectTesterFactory;

    public InstanceTester(final TestSuite testSuite, final AspectTesterFactory aspectTesterFactory) {
        this.testSuite = testSuite;
        this.aspectTesterFactory = aspectTesterFactory;
    }

    @Override
    public final void run() {
        for (final TestCase testCase : testSuite) {
            //before
            runThe(testCase);
            //after
        }
    }

    private void runThe(final TestCase testCase) {
        getRunnerFor(testCase).run();
    }

    private Tester getRunnerFor(final TestCase testCase) {
        return aspectTesterFactory.createFor(testCase);
    }
}
