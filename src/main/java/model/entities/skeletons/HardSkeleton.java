package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class HardSkeleton extends Skeleton {

    public HardSkeleton(int lane, Map map) {
        super(20, lane, 1, 0.04, map, "src/main/resources/skelHard.png");
    }
}