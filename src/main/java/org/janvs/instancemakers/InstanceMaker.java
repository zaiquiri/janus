package org.janvs.instancemakers;

import java.util.Collection;

public interface InstanceMaker {

    Collection<Object> createInstancesOf(Class<?> implementor);

}
