package cn.tedu.eliminate;

import javax.swing.*;

/**
 * 鸟
 */
public class Bird extends Element{

    public Bird(int x, int y) {
        super(x, y);
    }

    @Override
    public ImageIcon getImage() {
        return Images.bird;
    }
}
