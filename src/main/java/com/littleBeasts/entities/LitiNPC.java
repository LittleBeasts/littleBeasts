package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import org.json.JSONObject;
import utilities.JsonReader;

public class LitiNPC extends Creature {

    private String name;

    public LitiNPC(String name) {
        this.name = name;
    }

    private JSONObject getDialogtree() {
        return JsonReader.readJson("dialogueTree.JSON");
    }

    @Override
    public String toString() {
        return "LitiNPC{" +
                "name='" + name + '\'' +
                '}';
    }
}
