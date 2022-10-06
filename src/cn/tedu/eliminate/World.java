package cn.tedu.eliminate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class World extends JPanel {
    public static final int WIDTH = 429; // 窗口的宽度
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
    public Element createElement(int row, int col) {
        int x = OFFSET + col * ELEMENT_SIZE; // 列col的值控制x坐标
        int y = OFFSET + row * ELEMENT_SIZE; // 行row的值控制y坐标
        /*
        假设：
        1）row=0 col=0 x=30 y=30
        2）row=0 col=1 x=90 y=30
        3）row=0 clo=2 x=150 y=30
        ……
         */
        Random random = new Random(); // 随机数对象
        int type = random.nextInt(4); // 0-3之间
        switch (type) { // 根据type不同来生成不同的对象
            case ELEMENT_TYPE_BEAR:
                return new Bear(x, y);
            case ELEMENT_TYPE_BIRD:
                return new Bird(x, y);
            case ELEMENT_TYPE_FOX:
                return new Fox(x, y);
            default:
                return new Frog(x, y);
        }
    }

    // 元素可消状态
    public static final int ELIMINATE_NON = 0; // 不可消除
    public static final int ELIMINATE_ROW = 1; // 行可消除
    public static final int ELIMINATE_COL = 2; // 列可消除

    public int canEliminate(int row, int col) {
        Element e = elements[row][col]; // 获取当前元素
        // 假设row=0 col=2
        // 判断横向
        if (col >= 2) {
            Element e1 = elements[row][col - 1]; // 获取当前元素前面第1个元素(0,1)
            Element e2 = elements[row][col - 2]; // 获取当前元素前面第2个元素(0,0)
            if (e != null && e1 != null && e2 != null) { // 若元素都不是null
                // 若当前元素与它前面的两个元素类型都相同
                if (e.getClass().equals(e1.getClass()) && e.getClass().equals(e2.getClass())) {
                    return ELIMINATE_ROW; // 返回1，表示行可消除
                }
            }
        }
        // 判断竖向
        if (row >= 2) {
            Element e1 = elements[row - 1][col]; // 获取当前元素上面的第1个元素
            Element e2 = elements[row - 2][col]; // 获取当前元素上面的第2个元素
            if (e != null && e1 != null && e2 != null) { // 若元素都不是null
                // 若当前元素与它上面的两个元素类型都相同
                if (e.getClass().equals(e1.getClass()) && e.getClass().equals(e2.getClass())) {
                    return ELIMINATE_COL; // 返回2，表示列可消除
                }
            }
        }
        return ELIMINATE_NON; // 返回0，表示不可消除
    }

    /**
     * 填充所有元素
     */
    public void fillAllElements() {
        for (int row = 0; row < ROWS; row++) { // 遍历所有行
            for (int col = 0; col < COLS; col++) { // 遍历所有列
                do {
                    Element e = createElement(row, col); // 创建元素
                    elements[row][col] = e; // 将元素填充到elements数组中
                }while (canEliminate(row,col)!=ELIMINATE_NON); // 若可消除则重新生成元素
            }
        }
    }

    private boolean canInteractive = true; // 可交互状态(默认为true)
    private int selectedNumber = 0;  // 已经选中的元素个数
    private int firstRow = 0; // 第一个选中元素ROW
    private int firstCol = 0; // 第一个选中元素COL
    private int secondRow = 0; // 第二个选中元素ROW
    private int secondCol =0; // 第二个选中元素COL

    /**
     * 启动执行程序
     */
    public void start() {
        fillAllElements(); //填充所有的元素

        MouseAdapter adapter = new MouseAdapter() {
            /**
             * 重写鼠标点击事件
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                // 若不可交互时，不响应鼠标事件
                if (!canInteractive) return;
                canInteractive = false; // 只要选中元素，就先将状态修改为不可交互
                selectedNumber++; // 表示选中元素个数增1
                if (selectedNumber==1){ //第一次选中
                    //...
                    canInteractive = true; // 可交互
                } else if (selectedNumber==2) { // 第二次选中
                    //...
                    canInteractive = true; //可交互(在某种条件下才变为可交互)
                    selectedNumber = 0; //选中个数归零
                }
                repaint(); // 重画(调用该paint()画)
            }
        }; // 鼠标侦听器
        this.addMouseListener(adapter); // 添加鼠标侦听

    }

    /**
     * 重写paint方法
     *
     * @param graphics the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics graphics) {
        Images.background.paintIcon(null, graphics, 0, 0); // 画背景图
        for (int row = 0; row < ROWS; row++) {  // 遍历所有行
            for (int col = 0; col < COLS; col++) {  // 遍历所有列
                Element e = elements[row][col];  // 根据行列获取元素
                if (e != null) {  // 若元素不是null
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
        frame.setSize(WIDTH + 8, HEIGHT + 18);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        world.start();  // 启动程序的执行
    }
}
