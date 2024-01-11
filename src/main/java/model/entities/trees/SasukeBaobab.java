package model.entities.trees;

import model.config.Map;
import model.entities.Projectile;
import model.entities.projectiles.DarkNuke;

public class SasukeBaobab extends Baobab {
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/SasukeBaobab.png";
    private static final int DEFAULT_COST = 1200;
    private static final int DEFAULT_HP = 15;
    private static final int DEFAULT_DAMAGE = 15;

    public SasukeBaobab(int line, int column, Map map) {
        super(line, column, map, DEFAULT_IMAGE_PATH, DEFAULT_COST, DEFAULT_DAMAGE);
        super.name = "DarkBaobab";
    }

    public void shoot() {
        Projectile projectile = new DarkNuke(this.getLine(), this.getColumn(), getMap());
        addProjectile(projectile);
    }

    public String toString() {
        return "S";
    }
}
