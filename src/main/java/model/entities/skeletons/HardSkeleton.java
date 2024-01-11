package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class HardSkeleton extends Skeleton {
    public static final int DEFAULT_HP = 50;
    public static final int DEFAULT_SPEED = 1;
    public static final double DEFAULT_REAL_SPEED = 0.005;
    public static final String DEFAULT_PATH = "src/main/resources/skelHard.png";

    public HardSkeleton(int lane, Map map) {
        super(DEFAULT_HP, lane, DEFAULT_SPEED, DEFAULT_REAL_SPEED, map, DEFAULT_PATH);
    }
}