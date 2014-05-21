package com.zaiquiri.janus;

import java.lang.reflect.Method;

public interface TestRunnerFactory {
    Tester createTester(Method test);
}
