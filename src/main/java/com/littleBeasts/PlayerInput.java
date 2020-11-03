package com.littleBeasts;


import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public final class PlayerInput {

    private PlayerInput() {
    }

    public static void init() {
        // make the game exit upon pressing ESCAPE (by default there is no such key binding and the window needs to be shutdown otherwise, e.g. ALT-F4 on Windows)
        AtomicBoolean menu = new AtomicBoolean(false);
        Input.keyboard().onKeyTyped(KeyEvent.VK_ESCAPE, e -> {
            if (Player.instance().getState() == PlayerState.LOCKED || Player.instance().isDead()) {
                return;
            }
            if (GameLogic.getState() == GameState.INGAME) {
                GameLogic.setState(GameState.INGAME_MENU);
                menu.set(true);
            } else {
                GameLogic.setState(GameState.INGAME);
                menu.set(false);
            }
        });
        Input.keyboard().onKeyTyped(KeyEvent.VK_ENTER, e -> {
            if (GameLogic.getState() == GameState.MENU) {
                return;
            }
            if (GameLogic.getState() == GameState.INGAME && menu.get() == false) {
                GameLogic.setState(GameState.INGAME_CHAT);
            }
            menu.set(false);
        });
    }
}
