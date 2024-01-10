package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;

public class PineTree extends Tree {
    public static final int cost = 50;
    public static final int hp = 1;
    public static final int damage = 1;

    public PineTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/treedef.png");
    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "P";
    }
}