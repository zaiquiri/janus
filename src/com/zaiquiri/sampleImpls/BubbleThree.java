package com.zaiquiri.sampleImpls;

public class BubbleThree implements Bubblable {


	public BubbleThree(){
	}
	
	@Override
	public String bubblify(String bubble) {
		if (bubble.contains("@")) throw new IllegalArgumentException();
		return "BUBBLES!!!!!";
	}
	



}
