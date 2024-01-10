package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class PineProjectile extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/PineProjectile.png";
    public static final int DEFAULT_DAMAGE = 1;

    public PineProjectile(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}