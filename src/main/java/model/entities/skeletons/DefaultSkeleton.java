package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class DefaultSkeleton extends Skeleton {

    public DefaultSkeleton(int lane, Map map) {
        super(10, lane, 1, 0.04, map, "src/main/resources/skeldef.png");
    }

    public DefaultSkeleton(int lane, Map map, String skelPath) {
        super(10, lane, 1, 0.04, map, skelPath);
    }

}
