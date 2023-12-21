package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;

public class Acacia extends Tree {

    public static final int cost = 50;
    public static final int hp = 20;
    public static final int damage = 0;

    public Acacia(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    //TODO make getters and setters in the parent class
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
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "A";
    }
}
