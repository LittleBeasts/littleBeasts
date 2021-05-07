package com.littleBeasts.entities;

import config.FontConstants;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONObject;
import utilities.JsonReader;
import utilities.LitiFonts;

import java.awt.*;
import java.util.Random;

public class LitiNPC extends Creature implements Interactable {

    private final String name;
    private final JSONObject dialogueTree;
    private Font speechBubbleFont;
    private final IEntity iEntity;

    public LitiNPC(IEntity iEntity) {
        this.iEntity = iEntity;
        this.name = iEntity.getName();
        this.dialogueTree = getDefaultAnswers(name.replace("NPC-", ""));
        this.setFont();
    }

    private JSONObject getDefaultAnswers(String name) {
        return getDialogtree().getJSONObject("default").getJSONObject(name);
    }

    private JSONObject getDialogtree() {
        return JsonReader.readJson("./JSON/dialogueTree.JSON");
    }

    public void getGreeting() {
        Random random = new Random();
        try {
            this.speechBubbleFont.getSize();
            SpeechBubble.create(iEntity, dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString(), SpeechBubble.DEFAULT_APPEARANCE, this.speechBubbleFont);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString();
    }

    private void setFont() {
        this.speechBubbleFont = FontConstants.DEFAULT_FONT;
        if (iEntity.getProperties().getProperty("font") != null) {
            String fontName = iEntity.getProperties().getProperty("font").getAsString();
            for (Font font : LitiFonts.getGameFonts()) {
                if (font.getName().equals(fontName))
                    this.speechBubbleFont = font;
            }
        }
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
