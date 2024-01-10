package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;
import model.entities.projectiles.Nuke;
import javax.swing.*;

public class Baobab extends Tree {
    public static final int cost = 800;
    public static final int hp = 10;
    public static final int damage = 10000;

    public Baobab(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/Baobab.png");
    }

    @Override
    public void shoot() {
        Projectile projectile = new Nuke(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);
    }

    public void update() {
        super.update();

    }

    public String toString() {
        return "B";
    }

}
