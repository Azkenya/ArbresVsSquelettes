package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class FastSkeleton extends Skeleton {
    public static final int DEFAULT_HP = 10;
    public static final int DEFAULT_SPEED = 1;
    public static final double DEFAULT_REAL_SPEED = 0.01;
    public static final String DEFAULT_PATH = "src/main/resources/skelFast.png";

    public FastSkeleton(int lane, Map map) {
        super(DEFAULT_HP, lane, DEFAULT_SPEED, DEFAULT_REAL_SPEED, map, DEFAULT_PATH);
    }

}
