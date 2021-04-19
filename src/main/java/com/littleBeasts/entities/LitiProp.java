package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;

import java.util.Collection;

public class LitiProp {

    PropState propState;
    private String name;

    public LitiProp(String name) {
        this.name = name;
    }

    private enum PropState {
        CLOSED,
        OPENED
    }

    @Override
    public String toString() {
        return "LitiProp{" +
                "propState=" + propState +
                ", name='" + name + '\'' +
                '}';
    }
}


