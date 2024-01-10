package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class ChainsawProjectile extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/ChainsawProjectile.png";
    public static final int DEFAULT_DAMAGE = Integer.MAX_VALUE;
    public static final int DEFAULT_HP = Integer.MAX_VALUE;

    public ChainsawProjectile(int line, int column, Map map) {
        super(DEFAULT_HP, line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}
