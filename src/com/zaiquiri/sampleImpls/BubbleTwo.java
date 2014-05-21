package com.zaiquiri.sampleImpls;

public class BubbleTwo implements Bubblable {

	public BubbleTwo(){
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
