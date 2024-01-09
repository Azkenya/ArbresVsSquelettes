package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class FastSkeleton extends Skeleton {

    public FastSkeleton(int lane, Map map) {
        super(10, lane, 1, 0.01, map, "src/main/resources/skelFast.png");
    }

}
