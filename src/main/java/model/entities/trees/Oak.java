package model.entities.trees;

import model.config.Map;
import model.entities.Tree;

public class Oak extends Tree {

    public static final int cost = 100;
    public static final int hp = 15;
    public static final int damage = 2;

    public Oak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/treedef.png");
    }

    public void update() {
        super.update();
    }

    public String toString() {
        return "O";
    }
}
