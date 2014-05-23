package org.janvs.samples.impls;


import org.janvs.samples.interfaces.Bubblable;

public class BubbleOne implements Bubblable {

    public BubbleOne(String string) {
    }

    public BubbleOne() {
    }

    public BubbleOne(int a) {
    }

    public BubbleOne(double a) {
    }

    public BubbleOne(double a, String b) {
    }


    @Override
    public String bubblify(String bubble) {
        if (bubble.contains("@")) throw new IllegalArgumentException();
        if (bubble == null) throw new NullPointerException();
        return "bubble" + bubble + "bubble";
    }


}
