package org.janvs.factories;

import org.janvs.specs.TestCase;
import org.janvs.junit.helpers.notifiers.TestNotifier;

public interface TestNotifierFactory {
    TestNotifier createNotifier(Object instance, TestCase test);
}
