package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class DarkNuke extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/DarkNuke.png";
    public static final int DEFAULT_DAMAGE = 15;

    public DarkNuke(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}
