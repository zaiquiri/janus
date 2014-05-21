package com.zaiquiri.janus;

import java.lang.reflect.Method;

public interface TesterFactory {
    Tester createTester(Method test);
}
