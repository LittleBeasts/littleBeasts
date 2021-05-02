package com.littleBeasts;

import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.*;
import com.littleBeasts.screens.ActionMenu;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public final class PlayerInput {

    public static void init() {
        AtomicBoolean menu = new AtomicBoolean(false);
        Input.keyboard().onKeyTyped(e -> {
            if (GameLogic.getState().equals(GameState.BATTLE))
                battleControls(e);
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

    private static void battleControls(KeyEvent e) {
        ActionMenu currentBattleMenu;
        if (Program.getIngameScreen().getHud().getBattleMenu().getCatchMenu().isFocused())
            currentBattleMenu = Program.getIngameScreen().getHud().getBattleMenu().getCatchMenu();
        else if (Program.getIngameScreen().getHud().getBattleMenu().getAttackMenu().isFocused())
            currentBattleMenu = Program.getIngameScreen().getHud().getBattleMenu().getAttackMenu();
        else if (Program.getIngameScreen().getHud().getBattleMenu().isFocused())
            currentBattleMenu = Program.getIngameScreen().getHud().getBattleMenu();
        else
            return;

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                Game.audio().playSound("Menu_change");
                currentBattleMenu.decPosition();
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                currentBattleMenu.getMenuChange().accept(false);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                Game.audio().playSound("Menu_change");
                currentBattleMenu.incPosition();
                break;
            case KeyEvent.VK_D:
            case KeyEvent.VK_E:
            case KeyEvent.VK_SPACE:
                Game.audio().playSound("Menu_pick");
                currentBattleMenu.confirm();
        }
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