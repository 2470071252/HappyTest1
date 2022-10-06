package cn.tedu.eliminate;

import javax.swing.*;
import java.awt.*;

/**
 * 元素
 */
public abstract class Element {
    private int x; // x坐标
    private int y;  // y坐标
    private boolean selected;  // 是否选中
    private boolean eliminated; // 是否可消除
    private int eliminatedIndex; // 爆炸动画图片起始下标
    /**
     * 构造方法
     */
    public Element(int x, int y) {
        this.x = x;
        this.y = y;
        selected = false;
        eliminated = false;
        eliminatedIndex = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * 是否是选中的
     * @return
     */
    public boolean isSelected() {
        return selected;  // 返回选中状态
    }

    /**
     * 设置选中状态
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * 是否是消除的
     * @return
     */
    public boolean isEliminated() {
        return eliminated;   // 返回消除状态
    }

    /**
     * 设置元素状态
     * @param eliminated
     */
    public void setEliminated(boolean eliminated) {
        this.eliminated = eliminated;
    }

    /**
     * 获取图片
     * @return ImageIcon
     */
    public abstract ImageIcon getImage();

    /**
     * 画元素 graphics为画笔
     * @param graphics
     */
    public void paintElement(Graphics graphics){
        if (isSelected()) {  // 若选中了
            graphics.setColor(Color.GREEN);  // 设置画笔颜色
            graphics.fillRect(this.x,this.y,World.ELEMENT_SIZE,World.ELEMENT_SIZE);  // 填充矩形
            this.getImage().paintIcon(null,graphics,this.x,this.y);  // 画图片
        } else if (isEliminated()) { // 若消除了
            if (eliminatedIndex< Images.bombs.length) { // 若没有到最后一张爆破图
                Images.bombs[eliminatedIndex++].paintIcon(null,graphics,this.x,this.y); // 画爆炸图·
            }
        }else {  // 正常画
            this.getImage().paintIcon(null,graphics,this.x,this.y);
        }
    }



}
