package com.littlebeasts.scenemanager;

import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.littlebeasts.scenemanager.ScriptParserHelper.parseScript;

public class ScenePlayerHelper {

    private static int day = 1;
    private static int scene = 1;
    private static Script script;

    public static void startScene(int day, int scene) throws SceneNotPossibleError {
        if (ScenePlayerHelper.day == day && ScenePlayerHelper.scene == scene) {
            script = parseScript(day, scene);
            if (!isScenePossible()) throw new SceneNotPossibleError();
            Program.getGameLogic().setState(GameState.DIALOGUE);
            playScene();
            turnPage();
        }
    }

    public static boolean isScenePossible() {
        List<String> charactersOnMap = getCharactersOnMap();
        List<String> missingCharacters = new ArrayList<>();
        String[] dialoguePartners = script.getDialoguePartner();
        for (String dialoguePartner : dialoguePartners) {
            if (!charactersOnMap.contains(dialoguePartner)) {
                missingCharacters.add(dialoguePartner);
            }
        }
        return missingCharacters.size() == 0;
    }

    public static void playScene() {
        script.getDialogue().startDialogue();
    }

    public static void turnPage() {
        scene++;
    }

    public static void newDay() {
        day++;
    }

    public static List<String> getCharactersOnMap() {
        Collection<IEntity> iEntities = Game.world().environment().getEntities();
        List<String> charactersOnMap = new ArrayList<>();
        for (IEntity iEntity : iEntities)
            charactersOnMap.add(iEntity.getName());
        return charactersOnMap;
    }

    public static Script getScript() {
        return script;
    }
}
