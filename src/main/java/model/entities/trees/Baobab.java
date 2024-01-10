package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;

public class Baobab extends Tree {
    public static final int cost = 250;
    public static final int hp = 10;
    public static final int damage = 10000;

    public Baobab(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/treedef.png");
    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "B";
    }

}
