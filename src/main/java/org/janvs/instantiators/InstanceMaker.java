package org.janvs.instantiators;

import java.util.Collection;

public interface InstanceMaker {

    Collection<Object> createInstancesOf(Class<?> implementor);

}
