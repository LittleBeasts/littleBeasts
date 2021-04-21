package com.littleBeasts.entities;

import com.littleBeasts.GameLogic;
import config.FontConstants;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONObject;
import utilities.JsonReader;

import java.awt.*;
import java.util.Random;

public class LitiNPC extends Creature {

    private final String name;
    private final JSONObject dialogueTree;
    private final IEntity iEntity;
    private Font speechBubbleFont;

    public LitiNPC(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.dialogueTree = getDefaultAnswers(name.replace("NPC-", ""));
        this.setFont();
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
            this.speechBubbleFont.getSize();
            SpeechBubble.create(this.iEntity, dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString(), SpeechBubble.DEFAULT_APPEARANCE, this.speechBubbleFont);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return dialogueTree.getJSONArray("greetings").get(random.nextInt(dialogueTree.getJSONArray("greetings").length())).toString();
    }

    private void setFont() {
        this.speechBubbleFont = FontConstants.DEFAULT_FONT;
        if (this.iEntity.getProperties().getProperty("font") != null) {
            String fontName = this.iEntity.getProperties().getProperty("font").getAsString();
            for (Font font : GameLogic.getGameFonts()) {
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
}
