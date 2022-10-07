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
                } while (canEliminate(row, col) != ELIMINATE_NON); // 若可消除则重新生成元素
            }
        }
    }

    /**
     * 判断两个元素是否能交换（相邻则能交换，否则不能交换）
     */
    public boolean isAdjacent() {
        // 若行相邻并且列相等，或，若列相邻并且行相等
        if ((Math.abs(firstRow - secondRow) == 1 && firstCol == secondCol)
                || (Math.abs(firstCol - secondCol) == 1 && firstRow == secondRow)) {
            return true;  // 相邻
        } else {
            return false;  // 不相邻
        }
    }

    /**
     * 移动两个元素
     */
    public void moveElement() {
        if (firstRow == secondRow) { // 若行号相同，表示左右动
            int firstX = OFFSET + firstCol * ELEMENT_SIZE; // 第一个元素的x坐标
            int secondX = OFFSET + secondCol * ELEMENT_SIZE; // 第二个元素的x坐标
            int step = firstX < secondX ? 4 : -4;  // 步长(控制左右)，值越大速度越快
            for (int i = 0; i < 15; i++) { // 走15次（每次走4，共15*4为60）
                try { // 每动一下休眠10ms
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                firstX += step; // firstX加
                secondX -= step; // secondX减
                // 修改元素坐标（刚刚只是在修改变量的值，一定是元素坐标修改之后才能看到移动）
                elements[firstRow][firstCol].setX(firstX);
                elements[secondRow][secondCol].setX(secondX);
                repaint();
            }
        }
        if (firstCol == secondCol) { // 若列号相同表示上下动
            int firstY = OFFSET + firstRow * ELEMENT_SIZE;
            int secondY = OFFSET + secondRow * ELEMENT_SIZE;
            int step = firstY < secondY ? 4 : -4;
            for (int i = 0; i < 15; i++) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                firstY += step;
                secondY -= step;
                elements[firstRow][firstCol].setY(firstY);
                elements[secondRow][secondCol].setY(secondY);
                repaint();
            }
        }
    }

    /**
     * 交换两个元素
     */
    public void exchangeElement() {
        Element e1 = elements[firstRow][firstCol]; // 获取第一个选中元素
        Element e2 = elements[secondRow][secondCol]; // 获取第二个选中元素
        elements[firstRow][firstCol] = e2; // 将第一个选中元素修改为第二个元素
        elements[secondRow][secondCol] = e1; // 将第二个选中的元素修改为第一个元素
    }

    /**
     * 扫描并删除元素
     *
     * @return
     */
    public boolean eliminateElement() {
        boolean haveEliminated = false; // 是否有元素被消除了

        for (int row = ROWS - 1; row >= 0; row--) { // 扫描所有行（76543210）
            for (int col = COLS - 1; col >= 0; col--) { // 扫描所有列（543210）
                Element e = elements[row][col]; // 获取当前元素
                if (e == null) continue; // 若元素为null，则跳过
                // 1) 查找一行中连续的个数，查找一列中连续的个数
                // 2) 将连续(可消除)的元素设计为可消除状态(因为要插入爆炸动画)
                // 3) 将可消除状态的元素绘制爆炸动画
                // 4) 将可消除状态元素设置为null，以等待其他元素的下落

                // 1) 查找一行中连续的个数，查找一列中连续的个数
                int colRepeat = 0; // 行不变，列相邻----与当前行元素相邻的行元素的连续重复个数
                for (int pc = col - 1; pc >= 0; pc--) {  // 倒着遍历当前元素中的所有列
                    if (elements[row][pc] == null) break; // 若前面的元素为null则直接退出
                    // 若遍历元素与当前元素相同，则重复个数加1，否则break推出
                    if (elements[row][pc].getClass() == e.getClass())
                        colRepeat++;
                    else break; // 只要有一个不同的，剩余的不再需要比较了
                }

                int rowRepeat = 0; // // 列不变，行相邻----与当前列元素相邻的行元素的连续重复个数
                for (int pr = row - 1; pr >= 0; pr--) {
                    if (elements[pr][col] == null) break;
                    if (elements[pr][col].getClass() == e.getClass())
                        rowRepeat++;
                    else break;
                }

                // 2) 将连续(可消除)的元素设计为可消除状态(因为要插入爆炸动画)
                if (colRepeat>=2) {  // 行不变，列相邻，条件内表示列可消除
                    elements[row][col].setEliminated(true);
                    for (int i=1;i<=colRepeat;i++){ //遍历连续个数次
                        elements[row][colRepeat-i].setEliminated(true); // 行不变，列前元素设置为可消除
                    }
                }
                if (rowRepeat>=2) { // 列不变，行相邻，条件内表示行可消除
                    elements[row][col].setEliminated(true);
                    for (int i =1;i<=rowRepeat;i++){
                        elements[row-i][col].setEliminated(true);
                    }
                }

                // 3) 将可消除状态的元素绘制爆炸动画
                if (colRepeat>=2||rowRepeat>=2){ // 如果有可消除元素
                    for (int i =0;i<Images.bombs.length;i++){  // 遍历所有爆破图
                        repaint(); // 重画，依次显示4张爆破图
                        try { // 每动一下休眠10ms
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                // 4) 将可消除状态元素设置为null，以等待其他元素的下落
                if (colRepeat>=2){  // 行不变，列相邻
                    elements[row][col] = null;
                    for (int i=1;i<colRepeat;i++){
                        elements[row][col-i] = null;
                    }
                    haveEliminated = true;
                }
                if (rowRepeat>=2){
                    elements[row][col] = null;
                    for (int j=1;j<rowRepeat;j++){
                        elements[row-j][col] = null;
                    }
                    haveEliminated = true;
                }

            }
        }

        return haveEliminated;
    }

    private boolean canInteractive = true; // 可交互状态(默认为true)
    private int selectedNumber = 0;  // 已经选中的元素个数
    private int firstRow = 0; // 第一个选中元素ROW
    private int firstCol = 0; // 第一个选中元素COL
    private int secondRow = 0; // 第二个选中元素ROW
    private int secondCol = 0; // 第二个选中元素COL

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
                int row = (e.getY() - OFFSET) / ELEMENT_SIZE; // 获取选中元素的row
                int col = (e.getX() - OFFSET) / ELEMENT_SIZE; // 获取选中元素的col
                selectedNumber++; // 表示选中元素个数增1
                if (selectedNumber == 1) { //第一次选中
                    firstRow = row;  // 记录第一个元素row
                    firstCol = col;  // 记录第一个元素col
                    elements[firstRow][firstCol].setSelected(true); // 设置选中状态为true
                    canInteractive = true; // 可交互

                } else if (selectedNumber == 2) { // 第二次选中
                    secondRow = row; // 记录第2个元素row
                    secondCol = col; // 记录第2个元素col
                    elements[secondRow][secondCol].setSelected(true);
                    if (isAdjacent()) { // 若相邻
                        new Thread(() -> {
                            elements[firstRow][firstCol].setSelected(false);
                            elements[secondRow][secondCol].setSelected(false);
                            // 消除
                            moveElement();  // 移动两个元素(移动动画)
                            exchangeElement(); // 交换两个元素
                            eliminateElement(); // 扫描并消除元素
                            canInteractive = true; // 可交互

                        }).start();

                    } else {  // 不相邻
                        elements[firstRow][firstCol].setSelected(false);  // 取消选中状态
                        elements[secondRow][secondCol].setSelected(false); // 取消选中状态
                        canInteractive = true;
                    }

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
