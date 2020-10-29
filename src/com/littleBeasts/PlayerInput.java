package com.littleBeasts;


import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

public final class PlayerInput {

    private PlayerInput() {
    }

    public static void init() {
        // make the game exit upon pressing ESCAPE (by default there is no such key binding and the window needs to be shutdown otherwise, e.g. ALT-F4 on Windows)
        Input.keyboard().onKeyPressed(KeyEvent.VK_ESCAPE, e -> {
            //System.exit(0);
            if (GameLogic.getState() == GameState.INGAME) {
                GameLogic.setState(GameState.INGAME_MENU);
                System.out.println("Menu");
            }else{
                GameLogic.setState(GameState.INGAME);
            }
        });
    }
}
