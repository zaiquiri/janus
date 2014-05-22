package com.zaiquiri.janus;

public interface TestNotifierFactory {
    TestNotifier createNotifier(Object instance, TestCase test);
}
