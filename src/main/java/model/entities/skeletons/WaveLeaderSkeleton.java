package model.entities.skeletons;

import model.config.Map;

public class WaveLeaderSkeleton extends DefaultSkeleton {
    public static final String DEFAULT_PATH = "src/main/resources/skelWaveLeader.png";
    public WaveLeaderSkeleton(int lane, Map map) {

        super(lane, map, DEFAULT_PATH);
    }

}