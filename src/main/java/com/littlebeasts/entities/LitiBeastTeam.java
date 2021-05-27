package com.littlebeasts.entities;

import calculationEngine.entities.CeEntity;

import java.util.ArrayList;
import java.util.List;

public class LitiBeastTeam {

    private List<LitiBeast> littleBeasts;

    public LitiBeastTeam() {
        this.littleBeasts = new ArrayList<>();
    }

    public List<LitiBeast> getBeasts() {
        return littleBeasts;
    }

    public void addBeast(LitiBeast litiBeast) {
        littleBeasts.add(litiBeast);
        int position = littleBeasts.indexOf(litiBeast);
        // ToDo: cleanUp Constructor and change Class Name (BeastStats)
        littleBeasts.get(position).createBeastStats();
    }

    public List<CeEntity> beastsToCeEntities(List<LitiBeast> litiBeasts) {
        List<CeEntity> entityList = new ArrayList<>();
        for (LitiBeast litiBeast : litiBeasts) {
            entityList.add(litiBeast.getCeEntity());
        }
        return entityList;
    }
}
