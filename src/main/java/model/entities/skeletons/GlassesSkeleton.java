package model.entities.skeletons;

import model.config.Map;
import model.entities.Skeleton;

public class GlassesSkeleton extends Skeleton {

    public GlassesSkeleton(int lane, Map map) {
        super(15, lane, 1, 0.005, map, "src/main/resources/SkelGlasses.png");
    }

}
