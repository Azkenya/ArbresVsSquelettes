package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;
import model.entities.projectiles.PineProjectile;

import javax.swing.*;

public class PineTree extends Tree {
    public static final int cost = 50;
    public static final int hp = 1;
    public static final int damage = 1;

    public PineTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/PineTree.png");
    }

    @Override
    public void shoot() {
        Projectile projectile = new PineProjectile(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);
    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "P";
    }
}