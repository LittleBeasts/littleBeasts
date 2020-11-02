package com.littleBeasts;


import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

public final class PlayerInput {

    private PlayerInput() {
    }

    public static void init() {
        // make the game exit upon pressing ESCAPE (by default there is no such key binding and the window needs to be shutdown otherwise, e.g. ALT-F4 on Windows)
        Input.keyboard().onKeyTyped(KeyEvent.VK_ESCAPE, e -> {
            if (Player.instance().getState() == PlayerState.LOCKED || Player.instance().isDead()) {
                return;
            }
            if (GameLogic.getState() == GameState.INGAME) {
                GameLogic.setState(GameState.INGAME_MENU);
            }else{
                GameLogic.setState(GameState.INGAME);
            }
        });
    }
}
