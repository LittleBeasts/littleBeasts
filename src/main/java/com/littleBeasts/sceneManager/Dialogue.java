package com.littleBeasts.sceneManager;

import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Dialogue {

    private final JSONArray jsonDialogue;
    private final List<DialogueToken> dialogueTokens = new ArrayList<>();
    private SpeechBubble speechBubble;

    public Dialogue(JSONArray jsonDialogue) {
        this.jsonDialogue = jsonDialogue;
        parseDialogue();
    }

    private void parseDialogue() {
        for (int i = 0; i < this.jsonDialogue.length(); i++) {
            dialogueTokens.add(new DialogueToken(new JSONObject(jsonDialogue.get(i).toString())));
        }
    }

    private static class DialogueToken {
        private final String speaker;
        private final String text;

        public DialogueToken(JSONObject jsonObject) {
            this.speaker = jsonObject.getString("speaker");
            this.text = jsonObject.getString("text");
        }
    }

    public void startDialogue() {
        if (this.dialogueTokens.size() > 0) {
            DialogueToken currentDialogueToken = this.dialogueTokens.remove(0);
            speechBubble = SpeechBubble.create(getSpeakerEntity(currentDialogueToken.speaker), currentDialogueToken.text);
            speechBubble.addListener(this::startDialogue);
        } else
            Program.getGameLogic().setState(GameState.INGAME);
    }

    public SpeechBubble getSpeechBubble() {
        return speechBubble;
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
