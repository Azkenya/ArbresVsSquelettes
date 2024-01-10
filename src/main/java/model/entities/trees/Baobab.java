package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;

import javax.swing.*;

public class Baobab extends Tree {
    public static final int cost = 250;
    public static final int hp = 10;
    public static final int damage = 10000;
    private int ProjectileCooldown = 0;
    public Baobab(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
        JLabel baobabImage = new JLabel(new ImageIcon("src/main/resources/baobab.png"));
        baobabImage.setBounds(column*111,line*200,111,200);
        this.setAttachedImage(baobabImage);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    @Override
    public int getPrice() {
        return super.getPrice();
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
        return "B";
    }

}
