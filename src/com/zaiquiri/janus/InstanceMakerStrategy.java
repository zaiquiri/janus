package com.zaiquiri.janus;

import java.util.Collection;

public interface InstanceMakerStrategy {

    Collection createInstancesOf(Class<?> implementor);

}
