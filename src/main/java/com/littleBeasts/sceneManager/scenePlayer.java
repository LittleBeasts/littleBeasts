package com.littleBeasts.sceneManager;

import com.littleBeasts.Program;
import com.littleBeasts.gameLogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.littleBeasts.sceneManager.scriptParser.parseScript;

public class scenePlayer {

    private static int day = 1, scene = 1;
    private static Script script;


    public static void startScene(int day, int scene) throws SceneNotPossibleError {
        if (scenePlayer.day == day && scenePlayer.scene == scene) {
            script = parseScript(day, scene);
            isScenePossible();
            Program.getGameLogic().setState(GameState.DIALOGUE);
            playScene();
            turnPage();
        }
    }

    public static void isScenePossible() throws SceneNotPossibleError {
        List<String> charactersOnMap = getCharactersOnMap();
        List<String> missingCharacters = new ArrayList<>();
        boolean sceneIsPossible = true;
        String[] dialoguePartners = script.getDialoguePartner();
        for (String dialoguePartner : dialoguePartners) {
            if (!charactersOnMap.contains(dialoguePartner)) {
                sceneIsPossible = false;
                missingCharacters.add(dialoguePartner);
            }
        }
        if (!sceneIsPossible) {
            throwSceneNotPossibleError(missingCharacters);
        }

    }

    private static void throwSceneNotPossibleError(List<String> missingCharacters) throws SceneNotPossibleError {
        StringBuilder missingCharactersString = new StringBuilder();
        for (String character : missingCharacters)
            missingCharactersString.append(character);
        throw new SceneNotPossibleError(missingCharactersString.toString());
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
