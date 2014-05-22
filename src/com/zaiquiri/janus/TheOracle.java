package com.zaiquiri.janus;

import java.util.Collection;

public class TheOracle {
    private final TestClassData testClassData;

    public TheOracle(final TestClassData testClassData) {
        this.testClassData = testClassData;
    }

    public void prayToJanus(final TestNotifierFactory andGiveUsASign) {
        ohPowerfulGodJanus(
                letOurSystem(),
                beAbleToMakeAllPossibleInstancesOfOurInterface(),
                runTheTestsForAllOfThem(andGiveUsASign));
    }

    private void ohPowerfulGodJanus(final Collection<Class<?>> allImplementors, final InstanceMaker instanceMaker, final ImplementationTesterFactory implementationTesterFactory) {
        new SystemTester(allImplementors, instanceMaker, implementationTesterFactory).run();
    }

    private DefaultImplementationTesterFactory runTheTestsForAllOfThem(final TestNotifierFactory testNotifierFactory) {
        return new DefaultImplementationTesterFactory(new DefaultInstanceTesterFactory(testClassData, testNotifierFactory));
    }

    private Collection<Class<?>> letOurSystem() {
        return new ClassFinder(testClassData.basePackage())
                .findImplementationsOf(
                        testClassData.interfaceUnderTest().getType());
    }

    private InstanceMaker beAbleToMakeAllPossibleInstancesOfOurInterface() {
        return new InstanceMaker(new ConstructorStrategy(), new FactoryStrategy());
    }

    public String getName() {
        return testClassData.getName();
    }
}
