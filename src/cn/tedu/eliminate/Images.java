package cn.tedu.eliminate;

import javax.swing.*;

public class Images extends ImageIcon {
    public static ImageIcon background;
    public static ImageIcon bear;
    public static ImageIcon bird;
    public static ImageIcon fox;
    public static ImageIcon frog;
    public static ImageIcon[] bombs;  // 爆炸图片数组

    static {
        background = new ImageIcon("img/background.png");
        bear = new ImageIcon("img/bear.png");
        bird = new ImageIcon("img/bird.png");
        fox = new ImageIcon("img/fox.png");
        frog = new ImageIcon("img/frog.png");
        bombs = new ImageIcon[4];
        for (int i = 0; i < bombs.length; i++) {
            bombs[i] = new ImageIcon("img/bom"+(i+1)+".png");
        }
    }

// 查看图片状态，都为8表示读取成功
    /*
    public static void main(String[] args) {
        System.out.println(background.getImageLoadStatus());
        System.out.println(bear.getImageLoadStatus());
        System.out.println(bird.getImageLoadStatus());
        System.out.println(fox.getImageLoadStatus());
        System.out.println(frog.getImageLoadStatus());
        for (int j = 0; j < bombs.length; j++) {
            System.out.println(bombs[j].getImageLoadStatus());
        }
    }
    */
}
