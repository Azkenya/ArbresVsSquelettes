package model.entities.trees;

import model.config.Map;
import model.entities.Tree;

import javax.swing.*;

public class Oak extends Tree {

    public static final int cost = 100;
    public static final int hp = 15;
    public static final int damage = 2;

    public Oak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
        JLabel oakImg = new JLabel(new ImageIcon("src/main/resources/treedef.png"));
        oakImg.setBounds(column * 111, line * 200, 111, 200);
        // oakImg.setBounds(column*55,line*100,55, 100);
        this.setAttachedImage(oakImg);
    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "O";
    }
}
