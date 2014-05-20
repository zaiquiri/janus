package com.zaiquiri.sampleImpls;


public class BubbleOne implements Bubblable {

    public BubbleOne(String string){
    }

    public BubbleOne(){
    }


	@Override
	public String bubblify(String bubble) {
		if (bubble.contains("@")) throw new IllegalArgumentException();
		return "bubble" + bubble + "bubble";
	}



}
