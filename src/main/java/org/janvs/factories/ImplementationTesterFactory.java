package org.janvs.factories;

import org.janvs.instantiators.InstanceMaker;
import org.janvs.testers.Tester;

import java.util.Collection;

import static org.janvs.testers.TypeCollectionTester.typeCollectionTester;

public class ImplementationTesterFactory implements Factory<Tester, Class> {

    private final Factory<Tester, Object> testerFactory;
    private final InstanceMaker instanceMaker;

    public ImplementationTesterFactory(final Factory<Tester, Object> testerFactory, final InstanceMaker instanceMaker) {
        this.testerFactory = testerFactory;
        this.instanceMaker = instanceMaker;
    }

    @Override
    public Tester createFor(final Class implementor) {
        return typeCollectionTester(instancesFor(implementor), testerFactory);
    }

    private Collection<Object> instancesFor(final Class implementor) {
        return instanceMaker.createInstancesOf(implementor);
    }
}
