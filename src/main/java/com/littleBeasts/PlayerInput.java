package com.littleBeasts;

import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.*;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public final class PlayerInput {

    public static void init() {
        AtomicBoolean menu = new AtomicBoolean(false);
        Input.keyboard().onKeyTyped(e -> {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ESCAPE:
                    onEscape(menu);
                    break;
                case KeyEvent.VK_ENTER:
                    onEnter(menu);
                    break;
                default:
                    onOtherKey(e);
            }
        });

    }

    private static void onEscape(AtomicBoolean menu) {
        if (LitiPlayer.instance().getState() == PlayerState.LOCKED || LitiPlayer.instance().isDead()) {
            return;
        }
        if (GameLogic.getState() == GameState.INGAME) {
            GameLogic.setState(GameState.INGAME_MENU);
            menu.set(true);
        } else {
            GameLogic.setState(GameState.INGAME);
            menu.set(false);
        }
    }

    private static void onEnter(AtomicBoolean menu) {
        if (!LitiClient.isOnlineGame())
            return;
        if (GameLogic.getState() == GameState.MENU) {
            return;
        }
        if (GameLogic.getState() == GameState.INGAME && !menu.get()) {
            GameLogic.setState(GameState.INGAME_CHAT);
        }
        menu.set(true);
    }

    private static void onOtherKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_B && GameLogic.getState() == GameState.INGAME && LitiBattle.isNextBattlePossible()) {
            GameLogic.setState(GameState.BATTLE);
        } else if (e.getKeyCode() == KeyEvent.VK_B && GameLogic.getState() == GameState.BATTLE) {
            GameLogic.setState(GameState.INGAME);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            LitiPlayer.instance().interact();
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {
            if (GameLogic.getState() == GameState.MENU) {
                return;
            }
            if (GameLogic.getState() == GameState.INGAME) {
                GameLogic.setState(GameState.INVENTORY);
            } else if (GameLogic.getState() == GameState.INVENTORY) {
                GameLogic.setState(GameState.INGAME);
            }
        }
    }
}