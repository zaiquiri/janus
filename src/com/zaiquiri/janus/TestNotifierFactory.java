package com.zaiquiri.janus;

import java.lang.reflect.Method;

public interface TestNotifierFactory {
    TestNotifier createNotifier(Method test);
}
