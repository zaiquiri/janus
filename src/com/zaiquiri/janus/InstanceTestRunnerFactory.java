package com.zaiquiri.janus;

public interface InstanceTestRunnerFactory {
    Runnable createRunnerFor(Object instance);
}
