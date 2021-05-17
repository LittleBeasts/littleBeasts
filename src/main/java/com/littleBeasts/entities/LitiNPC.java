package com.littleBeasts.entities;

import config.FontConstants;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONObject;

import java.awt.*;
import java.util.Random;

import static config.DialogueConstants.dialogueTree;

public class LitiNPC extends Creature implements Interactable {

    private final String name;
    private final JSONObject npcDialogueTree;
    private Font speechBubbleFont;
    private final IEntity iEntity;

    public LitiNPC(IEntity iEntity) {
        this.iEntity = iEntity;
        this.name = iEntity.getName();
        this.npcDialogueTree = getDefaultAnswers(name.replace("NPC-", ""));
    }

    private JSONObject getDefaultAnswers(String name) {
        return dialogueTree.getJSONObject("default").getJSONObject(name);
    }


    public void getGreeting() {
        Random random = new Random();
        try {
            this.speechBubbleFont.getSize();
            SpeechBubble.create(iEntity, npcDialogueTree.getJSONArray("greetings").get(random.nextInt(npcDialogueTree.getJSONArray("greetings").length())).toString(), SpeechBubble.DEFAULT_APPEARANCE, this.speechBubbleFont);
        } catch (Exception e) {
            System.out.println(e);
        }
        npcDialogueTree.getJSONArray("greetings").get(random.nextInt(npcDialogueTree.getJSONArray("greetings").length())).toString();
    }

    @Override
    public String toString() {
        return "LitiNPC{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void interact() {
        getGreeting();
    }

    @Override
    public IEntity getiEntity() {
        return this.iEntity;
    }
}
