package org.janvs.testers;

import org.janvs.annotations.JanusOptions;
import org.janvs.annotations.UnderTest;
import org.janvs.junit.Janus;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Janus.class)
@JanusOptions(
        basePackage = "org.janvs.testers"
)
public class TesterTest {

    @UnderTest
    private Tester tester;

    @Test
    public void shouldRun() {
        tester.run();
    }
}