package com.zaiquiri.janus;

import java.lang.reflect.Method;

public interface TesterFromMethodFactory {
    Tester createTester(Method test);
}
