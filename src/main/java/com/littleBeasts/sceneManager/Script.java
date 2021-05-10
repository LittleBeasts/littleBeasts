package com.littleBeasts.sceneManager;

import org.json.JSONArray;
import org.json.JSONObject;

public class Script {

    private  String header;
    private  String[] dialoguePartner;
    private Dialogue dialogue;

    public Script(String header, String[] dialoguePartner, JSONArray JSONDialogue) {
        this.header = header;
        this.dialoguePartner = dialoguePartner;
        this.dialogue = new Dialogue(JSONDialogue);
    }

    public String getHeader() {
        return header;
    }

    public String[] getDialoguePartner() {
        return dialoguePartner;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
