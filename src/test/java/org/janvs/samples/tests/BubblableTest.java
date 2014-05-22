package org.janvs.samples.tests;

import org.janvs.annotations.JanusOptions;
import org.janvs.annotations.UnderTest;
import org.janvs.junit.Janus;
import org.janvs.samples.interfaces.Bubblable;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(Janus.class)
@JanusOptions(
        basePackage = "org.janvs.samples"
)
public class BubblableTest {

    @UnderTest
    private Bubblable bubble;

    @Test
    public void shouldAdd() {
        String string = bubble.bubblify("bubble");
        assertNotNull(string);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowExceptionForNullBubble() {
        bubble.bubblify(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForAtCharacterBubble() {
        bubble.bubblify("@bubble");
    }


}
