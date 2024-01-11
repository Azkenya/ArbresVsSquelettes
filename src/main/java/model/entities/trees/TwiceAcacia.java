package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import model.entities.projectiles.*;
import controller.Game;

public class TwiceAcacia extends Tree {

    public static final int cost = 150;
    public static final int hp = 20;
    public static final int damage = 0;
    private int TerminalCooldown = 0;

    public TwiceAcacia(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/TwiceAcacia.png");
        super.name = "TwiceAcacia";
    }

    public TwiceAcacia(int line, int column, Map map, boolean fake) {
        super(cost, hp, line, column, damage, map, "src/main/resources/TwiceAcacia.png");
        super.name = "Acacia";
    }

    // TODO make getters and setters in the parent class
    public void update() {
        super.update();
        if (!Game.graphicMode) {
            if (TerminalCooldown == 3) {
                Game.addMoney();
                TerminalCooldown = 0;
            } else {
                TerminalCooldown++;
            }
        }
    }

    public void updateGraphic() {
        if (TerminalCooldown == 500) {
            Game.addMoney();
            Game.addMoney();
            TerminalCooldown = 0;
        } else {
            TerminalCooldown++;
        }
    }

    @Override
    public void shoot() {

    }

    public String toString() {
        return "T";
    }
}