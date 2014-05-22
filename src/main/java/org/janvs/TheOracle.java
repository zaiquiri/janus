package org.janvs;

import org.janvs.factories.DefaultImplementationTesterFactory;
import org.janvs.factories.DefaultInstanceTesterFactory;
import org.janvs.factories.ImplementationTesterFactory;
import org.janvs.factories.TestNotifierFactory;
import org.janvs.instantiators.InstanceMaker;
import org.janvs.instantiators.strategies.ConstructorStrategy;
import org.janvs.instantiators.strategies.FactoryStrategy;
import org.janvs.testers.SystemTester;
import org.janvs.util.ClassFinder;
import org.janvs.util.TestClassData;

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

    private void ohPowerfulGodJanus(final Collection<Class<?>> system, final InstanceMaker instanceMaker, final ImplementationTesterFactory implementationTesterFactory) {
        new SystemTester(system, instanceMaker, implementationTesterFactory).run();
    }

    private Collection<Class<?>> letOurSystem() {
        return new ClassFinder(basePackage()).findImplementationsOf(interfaceUnderTest());
    }

    private InstanceMaker beAbleToMakeAllPossibleInstancesOfOurInterface() {
        return new InstanceMaker(new ConstructorStrategy(), new FactoryStrategy());
    }

    private DefaultImplementationTesterFactory runTheTestsForAllOfThem(final TestNotifierFactory testNotifierFactory) {
        return new DefaultImplementationTesterFactory(instanceTesterFactory(testNotifierFactory));
    }

    private DefaultInstanceTesterFactory instanceTesterFactory(final TestNotifierFactory testNotifierFactory) {
        return new DefaultInstanceTesterFactory(testClassData, testNotifierFactory);
    }

    private String basePackage() {
        return testClassData.basePackage();
    }

    private Class<?> interfaceUnderTest() {
        return testClassData.interfaceUnderTest().getType();
    }

    public String getName() {
        return testClassData.getName();
    }
}
