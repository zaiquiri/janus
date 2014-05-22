package org.janvs.factories;

import org.janvs.specs.TestCase;
import org.janvs.specs.TestSuite;
import org.janvs.testers.Tester;

import static org.janvs.testers.TypeCollectionTester.typeCollectionTester;

public class InstanceTesterFactory implements Factory<Tester, Object> {
    private final TestSuite testSuite;
    private final Factory<Factory<Tester, TestCase>, Object> testerFactoryFactory;

    public InstanceTesterFactory(final TestSuite testSuite, final Factory<Factory<Tester, TestCase>, Object> testerFactoryFactory) {
        this.testerFactoryFactory = testerFactoryFactory;
        this.testSuite = testSuite;
    }

    @Override
    public Tester createFor(final Object implementationOfInterface) {
        return typeCollectionTester(testSuite.testCases(), testerFactory(implementationOfInterface));
    }

    private Factory<Tester, TestCase> testerFactory(final Object implementationOfInterface) {
        return testerFactoryFactory.createFor(implementationOfInterface);
    }
}
