package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class DefaultSkeleton extends Skeleton {
    public static final int DEFAULT_HP = 15;
    public static final int DEFAULT_SPEED = 1;
    public static final double DEFAULT_REAL_SPEED = 0.01;
    public static final String DEFAULT_PATH = "src/main/resources/skeldef.png";

    public DefaultSkeleton(int lane, Map map) {
        super(DEFAULT_HP, lane, DEFAULT_SPEED, DEFAULT_REAL_SPEED, map, DEFAULT_PATH);
    }

    public DefaultSkeleton(int lane, Map map, String skelPath) {
        super(DEFAULT_HP, lane, DEFAULT_SPEED, DEFAULT_REAL_SPEED, map, skelPath);
    }

}
