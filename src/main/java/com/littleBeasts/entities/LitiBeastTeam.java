package com.littleBeasts.entities;

import calculationEngine.entities.CeEntity;

import java.util.ArrayList;
import java.util.List;

public class LitiBeastTeam {

    private List<LitiBeast> littleBeastTeam;

    public LitiBeastTeam() {
        this.littleBeastTeam = new ArrayList<>();
    }

    public List<LitiBeast> getBeasts() {
        return littleBeastTeam;
    }

    public void addBeast(LitiBeast litiBeast) {
        littleBeastTeam.add(litiBeast);
        int position = littleBeastTeam.indexOf(litiBeast);
        // ToDo: cleanUp Constructor and change Class Name (BeastStats)
        littleBeastTeam.get(position).createBeastStats();
    }

    public List<CeEntity> beastsToCeEntities(List<LitiBeast> litiBeasts) {
        List<CeEntity> entityList = new ArrayList<>();
        for (LitiBeast litiBeast : litiBeasts) {
            entityList.add(litiBeast.getCeEntity());
        }
        return entityList;
    }
}