package com.littlebeasts.guicomponent;

import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import static config.HudConstants.MENU_FONT;
import static config.HudConstants.WIDTH;
import static config.HudConstants.HEIGHT;
import static config.HudConstants.MENU_BACKGROUND;
import static config.HudConstants.BUTTON_BLACK;
import static config.HudConstants.BUTTON_RED;
import static config.HudConstants.EMPTY_SLOT_PLACEHOLDER;


public class SaveMenu extends GuiComponent {
    //TODO load save games from json into arraylist savedGames
//    private ArrayList<Object> savedGames;
    private final String[] slots = {"Save 1", "Save 2", "Save 3", "Save 4"};
    private final Font menuFont = MENU_FONT;
    private int currentCursorPosition;
    private final int headerXPosition = (WIDTH / 8) + WIDTH / 3;
    private final int headerYPosition = HEIGHT / 10;
    private final int boxXPosition = WIDTH / 4;
    private final int initialBoxYPosition = (HEIGHT / 4);
    private final int boxWidth = WIDTH / 2;
    private final int boxHeight = HEIGHT / 20;
    private final int separatorXPosition = boxXPosition + boxWidth / 6;
    private final int shift = HEIGHT / 15;
    private final double slotNameXPosition = boxXPosition + (separatorXPosition - boxXPosition) / 2.5;
    private final int horizontalOffset = WIDTH / 10;
    private final GameState originMenu;
    private boolean focused;
    public SaveMenu(GameState menu) {
        super(0, 0, WIDTH, HEIGHT);
        Input.keyboard().onKeyTyped(e -> {
            if (Program.getGameLogic().getState().equals(GameState.SAVE_MENU)) {
                handleInput(e);
            }
        });
        this.originMenu = menu;
    }

    private void handleInput(KeyEvent e) {
        if(!focused) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                if (originMenu == GameState.MENU)
                    Program.getGameLogic().setState(GameState.MENU);
                else if (originMenu == GameState.INGAME_MENU)
                    Program.getGameLogic().setState(GameState.INGAME_MENU);
                focused = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                incrementCursorPosition();
                Game.audio().playSound("Menu_change");
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                decrementCursorPosition();
                Game.audio().playSound("Menu_change");
                break;
            case KeyEvent.VK_ENTER:
                loadOrSaveGame();
                Game.audio().playSound("Menu_pick");
                break;
            default:
                break;
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (Program.getGameLogic().getState().equals(GameState.SAVE_MENU)) {
            g.setColor(MENU_BACKGROUND);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            drawMenuHeader(g);
            drawSlots(g);
            drawSlotsDescription(g);
        }
    }


    private void drawMenuHeader(Graphics2D g) {
        g.setFont(menuFont);
        g.setColor(Color.WHITE);
        TextRenderer.render(g, "Save/Load Game", headerXPosition, headerYPosition);
    }

    private void drawSlots(Graphics2D g) {
        for (int i = 0; i < slots.length; i++) {
            // highlight selected slot
            if (i == currentCursorPosition)
                g.setColor(BUTTON_RED);
            else
                g.setColor(BUTTON_BLACK);
            // draw Slot Box

            g.fillRect(boxXPosition, initialBoxYPosition + (i + 1) * shift, boxWidth, boxHeight);
            g.setColor(Color.WHITE);
            // draw separator and slot name
            g.drawLine(separatorXPosition, initialBoxYPosition + (i + 1) * shift, separatorXPosition, initialBoxYPosition + (i + 1) * shift + boxHeight);
            TextRenderer.render(g, slots[i], slotNameXPosition, initialBoxYPosition + (i + 1) * shift + boxHeight / 2 + menuFont.getSize() / 2);
        }
    }

    private void drawSlotsDescription(Graphics2D g) {
        for (int i = 0; i < slots.length; i++) {
            String description = getSaveGameDescription();
            TextRenderer.render(g, description, separatorXPosition + horizontalOffset, initialBoxYPosition + (i + 1) * shift + boxHeight / 2 + menuFont.getSize() / 2);
        }
    }

    private void incrementCursorPosition() {
        if (currentCursorPosition + 1 == slots.length) {
            currentCursorPosition = 0;
        } else {
            currentCursorPosition++;
        }
    }

    private void decrementCursorPosition() {
        if (currentCursorPosition == 0) {
            currentCursorPosition = slots.length - 1;
        } else {
            currentCursorPosition--;
        }
    }

    private void loadOrSaveGame() {
//        if (savedGames.get(currentCursorPosition) == null) {
//            saveGame();
//        } else {
//            loadGame();
//        }
    }

    private String getSaveGameDescription() {
//        if (savedGames.get(i) != null) {
//            //TODO: read out and return saveGame stats
//            return savedGames.get(i).getDescription();
//        } else {
//            return EMPTY_SLOT_PLACEHOLDER;
//        }
        return EMPTY_SLOT_PLACEHOLDER;
    }

    public void setFocus(boolean focused) {
        this.focused = focused;
    }
}
