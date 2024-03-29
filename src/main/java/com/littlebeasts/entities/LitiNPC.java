package com.littlebeasts.entities;

import config.FontConstants;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONObject;
import utilities.LitiFontsUtils;

import java.awt.Font;
import java.awt.geom.Point2D;
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
        this.setFont();
    }

    private JSONObject getDefaultAnswers(String name) {
        return dialogueTree.getJSONObject("default").getJSONObject(name);
    }


    public void getGreeting() {
        Random random = new Random();
        SpeechBubble.create(iEntity, npcDialogueTree.getJSONArray("greetings").get(random.nextInt(npcDialogueTree.getJSONArray("greetings").length())).toString(), SpeechBubble.DEFAULT_APPEARANCE, this.speechBubbleFont);
    }

    private void setFont() {
        this.speechBubbleFont = FontConstants.DEFAULT_FONT;
        if (iEntity.getProperties().getProperty("font") != null) {
            String fontName = iEntity.getProperties().getProperty("font").getAsString();
            for (Font font : LitiFontsUtils.getGameFonts()) {
                if (font.getName().equals(fontName))
                    this.speechBubbleFont = font;
            }
        }
    }

    @Override
    public void interact() {
        getGreeting();
    }

    @Override
    public Point2D getCenter() {
        return iEntity.getCenter();
    }

    @Override
    public String toString() {
        return "LitiNPC{" +
                "name='" + name + '\'' +
                '}';
    }
}
