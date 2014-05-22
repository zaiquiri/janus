package org.janvs.instantiators.strategies;

import java.util.Collection;

public interface InstanceMakerStrategy {

    Collection createInstancesOf(Class<?> implementor);

}
