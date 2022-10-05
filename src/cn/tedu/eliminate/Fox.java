package cn.tedu.eliminate;

import javax.swing.*;

/**
 * 狐狸
 */
public class Fox extends Element{
    public Fox(int x, int y) {
        super(x, y);
    }

    @Override
    public ImageIcon getImage() {
        return Images.fox;
    }
}
