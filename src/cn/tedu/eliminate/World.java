package cn.tedu.eliminate;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class World extends JPanel {
    public static final int WIDTH =429; // 窗口的宽度
    public static final int HEIGHT = 570; // 窗口的高度
    public static final int ROWS = 8; // 8行
    public static final int COLS = 6; // 6列
    public static final int ELEMENT_SIZE = 60; // 元素大小
    public static final int OFFSET = 30; // 偏移量（第一个元素距离窗口左边缘的距离）
    private Element[][] elements = new Element[ROWS][COLS]; // 元素数组 （8行6列）

    // 元素类型
    public static final int ELEMENT_TYPE_BEAR = 0;  // 熊
    public static final int ELEMENT_TYPE_BIRD = 1; // 鸟
    public static final int ELEMENT_TYPE_FOX = 2;  // 狐狸
    public static final int ELEMENT_TYPE_FROG = 3; // 青蛙

    /**
     * 创建元素(根据行列来计算x/y坐标，而后创建对象)
     *
     * @param row
     * @param col
     */
    public Element createElement(int row, int col){
        int x = OFFSET+col*ELEMENT_SIZE; // 列col的值控制x坐标
        int y = OFFSET+row*ELEMENT_SIZE; // 行row的值控制y坐标
        /*
        假设：
        1）row=0 col=0 x=30 y=30
        2）row=0 col=1 x=90 y=30
        3）row=0 clo=2 x=150 y=30
        ……
         */
        Random random = new Random(); // 随机数对象
        int type = random.nextInt(4); // 0-3之间
        switch (type){ // 根据type不同来生成不同的对象
            case ELEMENT_TYPE_BEAR:
                return new Bear(x,y);
            case ELEMENT_TYPE_BIRD:
                return new Bird(x,y);
            case ELEMENT_TYPE_FOX:
                return new Fox(x,y);
            default:
                return new Frog(x,y);
        }
    }

    /**
     * 填充所有元素
     */
    public void fillAllElements(){

    }

    /**
     * 启动执行程序
     */
    public void start(){
        fillAllElements(); //填充所有的元素
    }

    /**
     * 重写paint方法
     * @param graphics  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics graphics){
        Images.background.paintIcon(null,graphics,0,0); // 画背景图
        for (int row=0; row<ROWS;row++){  // 遍历所有行
            for (int col=0; col<COLS;col++){  // 遍历所有列
                Element e = elements[row][col];  // 根据行列获取元素
                if (e!=null) {  // 若元素不是null
                    e.paintElement(graphics);  // 画元素
                }
            }
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        world.setFocusable(true);
        frame.add(world);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH+8,HEIGHT+18);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        world.start();  // 启动程序的执行
    }
}
