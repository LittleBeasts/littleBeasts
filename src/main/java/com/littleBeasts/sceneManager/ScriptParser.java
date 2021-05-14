package com.littleBeasts.sceneManager;

import org.json.JSONArray;
import org.json.JSONObject;

import static config.DialogueConstants.dialogueTree;

public class ScriptParser {

    public static Script parseScript(int day, int scene) {
        JSONObject JSONDay = dialogueTree.getJSONObject("day" + day);
        JSONObject JSONScene = JSONDay.getJSONObject("scene" + scene);
        String header = JSONScene.getString("heading");
        JSONArray JSONDialoguePartner = JSONScene.getJSONArray("dialoguePartner");
        String[] dialoguePartner = new String[JSONDialoguePartner.length()];
        for (int i = 0; i < JSONDialoguePartner.length(); i++) {
            dialoguePartner[i] = JSONDialoguePartner.get(i).toString();
        }
        return new Script(header, dialoguePartner, JSONScene.getJSONArray("dialogue"));
    }
}
