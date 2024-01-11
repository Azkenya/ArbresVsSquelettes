package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class DarkPineTreeProjectile extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/DarkPineProjectile.png";
    public static final int DEFAULT_DAMAGE = 2;

    public DarkPineTreeProjectile(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}