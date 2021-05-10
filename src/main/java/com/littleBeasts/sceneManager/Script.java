package com.littleBeasts.sceneManager;

import org.json.JSONArray;

public class Script {

    private final String header;
    private final String[] dialoguePartner;
    private final Dialogue dialogue;

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
