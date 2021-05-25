package com.littlebeasts.scenemanager;

public class SceneNotPossibleError extends Throwable {
    public SceneNotPossibleError() {
        super("Some characters are missing from the scene");
    }
}
