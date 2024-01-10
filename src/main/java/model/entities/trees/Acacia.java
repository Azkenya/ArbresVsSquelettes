package model.entities.trees;

import model.config.Map;
import model.entities.Tree;
import controller.Game;
import javax.swing.Timer;

public class Acacia extends Tree {

    public static final int cost = 50;
    public static final int hp = 20;
    public static final int damage = 0;
    private Timer MoneyCooldown;
    private int TerminalCooldown = 0;

    public Acacia(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/Acacia.png");
        initializeTimer();
        MoneyCooldown.start();
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
        Game.addMoney();
    }

    public String toString() {
        return "A";
    }

    public void initializeTimer() {
        this.MoneyCooldown = new Timer(10000, e -> this.updateGraphic());
        MoneyCooldown.setRepeats(true);
    }
}
