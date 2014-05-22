package org.janvs.samples.impls;

import org.janvs.samples.interfaces.Bubblable;

public class BubbleTwo implements Bubblable {

	public BubbleTwo(){
	}

    public static Bubblable bubblable() {
        return new BubbleTwo();
    }

    public static BubbleTwo bubbleTwo() {
        return new BubbleTwo();
    }

	@Override
	public String bubblify(String bubble) {
		if (bubble.contains("@")) throw new IllegalArgumentException();
        if (bubble == null) throw new NullPointerException();
		String bubblified = "";
		char[] bubbles = "bubble".toCharArray();
		int i = 0;
		for (char letter : bubble.toCharArray()){
			char bub = bubbles[i % bubbles.length];
			bubblified += (bub+letter);
			i++;
		}
		return bubblified;
	}


}
