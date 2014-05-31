package org.janvs.junit.helpers.notifiers;

public interface TestNotifier {
    void testStarted();
    void testFailed(Exception e);
    void testFinished();
}
