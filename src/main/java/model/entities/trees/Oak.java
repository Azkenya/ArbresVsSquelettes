package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;

import javax.swing.*;

public class Oak extends Tree {


    public static final int cost = 100;
    public static final int hp = 15;
    public static final int damage = 2;

    public Oak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
        JLabel tree = new JLabel(new ImageIcon("src/main/resources/tree.png"));
        tree.setBounds(line,column,200,200);
        this.setAttachedImage(tree);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    public int getHp() {
        return super.getHp();
    }

    public void setHp(int hp) {
        super.setHp(hp);
    }

    public int getDamage() {
        return super.getDamage();
    }

    public void setDamage(int damage) {
        super.setDamage(damage);
    }

    public void update() {
        super.update();
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "O";
    }
}
