package org.janvs.samples.impls;

import org.janvs.samples.interfaces.Bubblable;

import java.util.ArrayList;

public class BubbleTwo implements Bubblable {

	public static BubbleTwo BubbleTwoMaker1(){
        return new BubbleTwo();
    }

    public static BubbleTwo BubbleTwoMaker2(String string){
        return new BubbleTwo();
    }

    public static BubbleTwo BubbleTwoMaker3(boolean bool){
        return new BubbleTwo();
    }

    public static BubbleTwo BubbleTwoMaker3(int i, ArrayList a){
        return new BubbleTwo();
    }

    private BubbleTwo(){}
	
	@Override
	public String bubblify(String bubble) {
		if (bubble.contains("@")) throw new IllegalArgumentException();
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
