package com.littleBeasts;

import com.littleBeasts.actionMenu.ActionMenu;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.LitiBattle;
import com.littleBeasts.gameLogic.LitiClient;
import com.littleBeasts.gameLogic.PlayerState;
import com.littleBeasts.sceneManager.ScenePlayer;
import com.littleBeasts.screens.IngameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.concurrent.atomic.AtomicBoolean;

public final class PlayerInput {

    public static void init() {
        AtomicBoolean menu = new AtomicBoolean(false);
        Input.keyboard().onKeyTyped(e -> {
            if (Program.getGameLogic().getState().equals(GameState.BATTLE)) {
                battleControls(e);
            } else if (Program.getGameLogic().getState().equals(GameState.DIALOGUE) && e.getKeyCode() == KeyEvent.VK_E) {
                ScenePlayer.getScript().getDialogue().getSpeechBubble().hide();
            } else if (Program.getGameLogic().getState().equals(GameState.INVENTORY)) {
                inventoryControls(e);
            } else {
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
            }
        });
    }

    private static void inventoryControls(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                IngameScreen.getInventory().incrementCursorPosition();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                IngameScreen.getInventory().decrementCursorPosition();
                break;
            case KeyEvent.VK_I:
            case KeyEvent.VK_ESCAPE:
                Program.getGameLogic().setState(GameState.INGAME);
        }
    }

    private static void battleControls(KeyEvent e) {
        ActionMenu currentBattleMenu;
        if (getFocusedMenu() == null) return;
        currentBattleMenu = getFocusedMenu();

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
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
            case KeyEvent.VK_E:
            case KeyEvent.VK_SPACE:
                Game.audio().playSound("Menu_pick");
                currentBattleMenu.confirm();
        }
    }

    private static ActionMenu getFocusedMenu() {
        if (Program.getIngameScreen().getHud().getBattleMenu().getCatchMenu().isFocused())
            return Program.getIngameScreen().getHud().getBattleMenu().getCatchMenu();
        else if (Program.getIngameScreen().getHud().getBattleMenu().getAttackMenu().isFocused())
            return Program.getIngameScreen().getHud().getBattleMenu().getAttackMenu();
        else if (Program.getIngameScreen().getHud().getBattleMenu().isFocused())
            return Program.getIngameScreen().getHud().getBattleMenu();
        else if (Program.getIngameScreen().getHud().getBattleMenu().getItemMenu().isFocused())
            return Program.getIngameScreen().getHud().getBattleMenu().getItemMenu();
        else return null;
    }

    private static void onEscape(AtomicBoolean menu) {
        if (LitiPlayer.instance().getState() == PlayerState.LOCKED || LitiPlayer.instance().isDead()) {
            return;
        }
        if (Program.getGameLogic().getState() == GameState.INGAME) {
            Program.getGameLogic().setState(GameState.INGAME_MENU);
            menu.set(true);
        } else {
            Program.getGameLogic().setState(GameState.INGAME);
            menu.set(false);
        }
    }

    private static void onEnter(AtomicBoolean menu) {
        if (!LitiClient.isOnlineGame())
            return;
        if (Program.getGameLogic().getState() == GameState.MENU) {
            return;
        }
        if (Program.getGameLogic().getState() == GameState.INGAME && !menu.get()) {
            Program.getGameLogic().setState(GameState.INGAME_CHAT);
        }
        menu.set(true);
    }

    private static void onOtherKey(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_B && Program.getGameLogic().getState() == GameState.INGAME && LitiBattle.isNextBattlePossible()) {
            Program.getGameLogic().setState(GameState.BATTLE);
        } else if (e.getKeyCode() == KeyEvent.VK_B && Program.getGameLogic().getState() == GameState.BATTLE) {
            Program.getGameLogic().setState(GameState.INGAME);
        }
        if (e.getKeyCode() == KeyEvent.VK_E) {
            LitiPlayer.instance().interact();
        }
        if (e.getKeyCode() == KeyEvent.VK_I) {
            if (Program.getGameLogic().getState() == GameState.MENU) {
                return;
            }
            if (Program.getGameLogic().getState() == GameState.INGAME) {
                Program.getGameLogic().setState(GameState.INVENTORY);
            } else if (Program.getGameLogic().getState() == GameState.INVENTORY) {
                Program.getGameLogic().setState(GameState.INGAME);
            }
        }
    }
}