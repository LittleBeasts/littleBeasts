package com.littleBeasts.sceneManager;

public class SceneNotPossibleError extends Throwable {
    public SceneNotPossibleError(String missingCharacters) {
        super("Following characters are missing from the scene: " + missingCharacters);
    }
}
