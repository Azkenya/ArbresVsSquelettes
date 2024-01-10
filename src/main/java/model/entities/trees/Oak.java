package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import model.entities.projectiles.*;
import model.entities.Projectile;

public class Oak extends Tree {

    public static final int cost = 100;
    public static final int hp = 15;
    public static final int damage = 2;

    public Oak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/Oak.png");
    }

    public void update() {
        super.update();
    }

    @Override
    public void shoot() {
        Projectile projectile = new Nuke(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);
    }

    public String toString() {
        return "O";
    }
}
