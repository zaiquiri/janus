package org.janvs;

import org.janvs.factories.*;
import org.janvs.instantiators.*;
import org.janvs.junit.helpers.notifiers.TestNotifier;
import org.janvs.specs.TestCase;
import org.janvs.testers.Tester;
import org.janvs.util.ClassFinder;
import org.janvs.util.TestClassData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.janvs.testers.TypeCollectionTester.typeCollectionTester;

public class JanusEngine {
    private final TestClassData testClassData;

    public JanusEngine(final TestClassData testClassData) {
        this.testClassData = testClassData;
    }

    public void runWith(final Factory<Factory<TestNotifier, TestCase>, Object> andGiveUsASign) {
        typeCollectionTester(new ClassFinder(basePackage()).findImplementationsOf(interfaceUnderTest()), runTheTestsForAllOfThem(andGiveUsASign)).run();
    }

    private static InstanceMaker instanceMaker() {
        return new CompositeInstanceMaker(
                new TypeInstanceMaker<Constructor>(new ConstructorInstanceMakerAdapter()),
                new TypeInstanceMaker<Method>(new FactoryInstanceMakerAdapter()));
    }

    private Factory<Tester, Class> runTheTestsForAllOfThem(final Factory<Factory<TestNotifier, TestCase>, Object> testNotifierFactoryFactory) {
        return new ImplementationTesterFactory(instanceTesterFactory(testNotifierFactoryFactory), instanceMaker());
    }

    private Factory<Tester, Object> instanceTesterFactory(final Factory<Factory<TestNotifier, TestCase>, Object> testNotifierFactoryFactory) {
        return new InstanceTesterFactory(testClassData.testSuite(), aspectTesterFactoryFactory(testNotifierFactoryFactory));
    }

    private Factory<Factory<Tester, TestCase>, Object> aspectTesterFactoryFactory(final Factory<Factory<TestNotifier, TestCase>, Object> testNotifierFactoryFactory) {
        return new AspectTesterFactoryFactory(testClassData.testClass(), testNotifierFactoryFactory, new FieldInjectorFactory(testClassData));
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
