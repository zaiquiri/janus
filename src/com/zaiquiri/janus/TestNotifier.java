package com.zaiquiri.janus;

public interface TestNotifier {
    void testStarted();
    void testFailed(Exception e);
    void testSucceeded();
}
