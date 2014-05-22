package org.janvs.factories;

import org.janvs.testers.Tester;

public interface ImplementationTesterFactory {
    public Tester createFor(Iterable<Object> instances);
}
