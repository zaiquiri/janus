package org.janvs.samples.impls;

import org.janvs.samples.interfaces.Bubblable;

public class BubbleThree implements Bubblable {


	public BubbleThree(){
	}
	
	@Override
	public String bubblify(String bubble) {
		if (bubble.contains("@")) throw new IllegalArgumentException();
        if (bubble == null) throw new NullPointerException();
		return "BUBBLES!!!!!";
	}
	



}
