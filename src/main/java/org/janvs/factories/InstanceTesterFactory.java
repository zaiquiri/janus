package org.janvs.factories;

import org.janvs.testers.Tester;

public interface InstanceTesterFactory {
    Tester createFor(Object instance);
}
