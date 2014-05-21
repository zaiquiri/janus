package com.zaiquiri.janus;

public interface TestCaseRunnerFactory {
    Runnable createRunnerFor(TestCase test);
}
