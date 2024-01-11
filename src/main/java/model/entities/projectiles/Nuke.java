package model.entities.projectiles;

import model.config.Map;
import model.entities.Projectile;

public class Nuke extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/Nuke.png";
    public static final int DEFAULT_DAMAGE = 8;

    public Nuke(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

}
