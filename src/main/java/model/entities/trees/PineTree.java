package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;

import javax.swing.*;

public class PineTree extends Tree {
    public static final int cost = 50;
    public static final int hp = 1;
    public static final int damage = 1;

    public PineTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
        JLabel pineImage = new JLabel(new ImageIcon("src/main/resources/pinetree.png"));
        pineImage.setBounds(column*111,line*200,111,200);
        this.setAttachedImage(pineImage);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    @Override
    public int getPrice() {
        return 10;
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
        this.updateGraphic();
    }

    public void updateGraphic() {
        this.shoot();
    }

    public void shoot() {
        Projectile projectile = new Projectile(this.getLine(), this.getColumn(), this.getDamage(), this.getMap());
        super.addProjectile(projectile);
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "P";
    }
}