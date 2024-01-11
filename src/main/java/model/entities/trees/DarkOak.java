package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import model.entities.projectiles.*;
import model.entities.Projectile;

public class DarkOak extends Tree {
    public static final int cost = 200;
    public static final int hp = 20;
    public static final int damage = 4;

    public DarkOak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/DarkOak.png");
        super.name = "DarkOak";
    }

    @Override
    public void shoot() {
        Projectile projectile = new DarkOakProjectile(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);
    }

    public String toString() {
        return "D";
    }

}
