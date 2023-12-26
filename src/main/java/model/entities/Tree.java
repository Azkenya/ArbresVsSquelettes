package model.entities;
import model.Entity;
import model.config.Map;

import javax.swing.*;

public abstract class Tree extends Entity {
    private int cost;
    private JLabel attachedImage;

    public Tree(int cost, int hp, int line, int column, int damage, Map map) {
        super(hp, line, column, damage, map);
        this.cost = cost;
    }

    @Override
    public void update() {
        var enemy = this.getMap().getFirstSkeletonInLine(this.getLine());
        if (enemy != null) {
            this.attack(enemy);
        }
    }

    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    public int getPrice() {
        return this.cost;
    }

    public int getCost() {
        return cost;
    }
    public int getHp() {
        return super.getHp();
    }

    public int getDamage() {
        return super.getDamage();
    }

    public JLabel getAttachedImage() {
        return attachedImage;
    }

    public void setAttachedImage(JLabel attachedImage) {
        this.attachedImage = attachedImage;
    }
}
