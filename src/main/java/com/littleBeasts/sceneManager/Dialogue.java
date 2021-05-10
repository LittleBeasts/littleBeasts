package com.littleBeasts.sceneManager;

import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import de.gurkenlabs.litiengine.gui.SpeechBubbleListener;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Dialogue {

    JSONArray jsonDialogue;
    List<DialogueToken> dialogueTokens = new ArrayList<>();

    public Dialogue(JSONArray jsonDialogue) {
        this.jsonDialogue = jsonDialogue;
        parseDialogue();
    }

    private void parseDialogue() {
        for (int i = 0; i < this.jsonDialogue.length(); i++) {
            dialogueTokens.add(new DialogueToken(new JSONObject(jsonDialogue.get(i).toString())));
        }
    }

    private class DialogueToken {
        private String speaker, text;

        public DialogueToken(JSONObject jsonObject) {
            this.speaker = jsonObject.getString("speaker");
            this.text = jsonObject.getString("text");
        }
    }

    public void startDialogue() {
        if (this.dialogueTokens.size() > 0) {
            DialogueToken currentDialogueToken = this.dialogueTokens.remove(0);
            SpeechBubble speechBubble = SpeechBubble.create(getSpeakerEntity(currentDialogueToken.speaker), currentDialogueToken.text);
            speechBubble.addListener(this::startDialogue);
        }
    }

    private IEntity getSpeakerEntity(String entityName) {
        if (entityName.equals("$name"))
            return LitiPlayer.instance();
        if (entityName.equals("$nameCat"))
            return LitiPet.instance();
        else return getEntityToName(entityName);
    }

    private IEntity getEntityToName(String entityName) {
        Collection<IEntity> iEntities = Game.world().environment().getEntities();
        IEntity returnEntity = null;
        for (IEntity iEntity : iEntities) {
            if (iEntity.getName().equals(entityName))
                returnEntity = iEntity;
        }
        return returnEntity;
    }
}
