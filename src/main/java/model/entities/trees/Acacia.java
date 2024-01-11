package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import controller.Game;

public class Acacia extends Tree {

    public static final int cost = 50;
    public static final int hp = 20;
    public static final int damage = 0;
    private int TerminalCooldown = 0;

    public Acacia(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/Acacia.png");
    }

    public Acacia(int line, int column, Map map, boolean fake) {
        super(cost, hp, line, column, damage, map, "src/main/resources/Acacia.png");
    }

    // TODO make getters and setters in the parent class
    public void update() {
        super.update();
        if (!Game.graphicMode) {
            if (TerminalCooldown == 2) {
                Game.addMoney();
                TerminalCooldown = 0;
            } else {
                TerminalCooldown++;
            }
        }
    }

    public void updateGraphic() {
        if (TerminalCooldown == 400) {
            Game.addMoney();
            TerminalCooldown = 0;
        } else {
            TerminalCooldown++;
        }
    }

    public String toString() {
        return "A";
    }
}
