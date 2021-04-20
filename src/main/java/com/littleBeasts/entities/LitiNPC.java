package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONObject;
import utilities.JsonReader;

import java.util.Random;

public class LitiNPC extends Creature {

    private String name;
    private JSONObject dialogueTree;
    private IEntity iEntity;

    public LitiNPC(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.dialogueTree = getDefaultAnswers(name.replace("NPC-", ""));
    }

    private JSONObject getDefaultAnswers(String name) {
        return getDialogtree().getJSONObject("default").getJSONObject(name);
    }

    private JSONObject getDialogtree() {
        return JsonReader.readJson("./JSON/dialogueTree.JSON");
    }

    public String getGreeting() {
        Random random = new Random();
        try {
            SpeechBubble.create(this.iEntity, dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString();
    }

    @Override
    public String toString() {
        return "LitiNPC{" +
                "name='" + name + '\'' +
                '}';
    }
}
