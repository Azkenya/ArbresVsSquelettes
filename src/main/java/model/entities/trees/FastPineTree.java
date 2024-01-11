package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;
import model.entities.projectiles.DarkPineTreeProjectile;

import javax.swing.*;

public class FastPineTree extends Tree {
    public static final int cost = 150;
    public static final int hp = 1;
    public static final int damage = 2;

    public FastPineTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/FastPineTree.png");
        super.name = "FastPine";
    }

    @Override
    public void shoot() {
        Projectile projectile = new DarkPineTreeProjectile(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);

    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "F";
    }
}