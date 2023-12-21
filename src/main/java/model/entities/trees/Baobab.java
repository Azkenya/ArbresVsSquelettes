package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;

public class Baobab extends Tree {
    public static final int cost = 250;
    public static final int hp = 10;
    public static final int damage = 10000;

    public Baobab(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
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
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "B";
    }

}
