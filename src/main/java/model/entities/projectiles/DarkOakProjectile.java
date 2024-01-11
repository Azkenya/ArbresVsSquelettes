package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class DarkOakProjectile extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/DarkOakProjectile.png";
    public static final int DEFAULT_DAMAGE = 4;

    public DarkOakProjectile(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}