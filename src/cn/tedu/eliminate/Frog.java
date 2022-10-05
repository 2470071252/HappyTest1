package cn.tedu.eliminate;

import javax.swing.*;

/**
 * 青蛙
 */
public class Frog extends Element{

    public Frog(int x, int y) {
        super(x, y);
    }

    @Override
    public ImageIcon getImage() {
        return Images.frog;
    }
}
