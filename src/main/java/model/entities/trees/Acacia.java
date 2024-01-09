package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import controller.Game;

public class Acacia extends Tree {

    public static final int cost = 50;
    public static final int hp = 20;
    public static final int damage = 0;
    private int MoneyCooldown = 0;

    public Acacia(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    // TODO make getters and setters in the parent class

    public void setHp(int hp) {
        super.setHp(hp);
    }

    public void setDamage(int damage) {
        super.setDamage(damage);
    }

    public void update() {
        this.updateGraphic();
    }

    public void updateGraphic() {
        if (this.MoneyCooldown == 0) {
            Game.addMoney();
            this.MoneyCooldown = 10;
        } else {
            this.MoneyCooldown--;
        }
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "A";
    }
}
