package com.zaiquiri.janus;

import static org.junit.Assert.assertNotNull;

import com.zaiquiri.sampleImpls.Bubblable;
import org.junit.*;
import org.junit.runner.RunWith;

@RunWith(Janus.class)
@JanusOptions(
        basePackage="com.zaiquiri"
)
public class BubblableTest {
	
	@UnderTest
	private Bubblable bubble;

	@Test
	public void shouldAdd(){
		String string = bubble.bubblify("bubble");
		assertNotNull(string);
	}
	
	@Test(expected = NullPointerException.class)
	public void shouldThrowExceptionForNullBubble(){
		bubble.bubblify(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowExceptionForAtCharacterBubble(){
		bubble.bubblify("@bubble");
	}


}
