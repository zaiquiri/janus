package org.janvs.factories;

import org.janvs.specs.TestCase;
import org.janvs.testers.Tester;

public interface AspectTesterFactory {
    Tester createFor(TestCase test);
}
