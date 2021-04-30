package com.littleBeasts.screens;

import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;
import config.PlayerConfig;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;

/*--------------------------------------------
This class creates a menu for use in battle.
It is called in the HUD class.
Height, width and bottom pad should be adjusted in HudConstants

20201105 D.B. Created Class
--------------------------------------------*/

public class DrawBattleMenu { // dev constructor
    private final int y, width;
    private int x, amountOfItems, amountOfDrawnItems, height;
    private int firstDrawnItem, lastDrawnItem, currentPosition;
    private ArrayList<String> items;
    private boolean focus = false;
    private final List<Consumer<Integer>> confirmConsumer;

    public DrawBattleMenu() {
        this.items = new ArrayList<>();
        this.x = BATTLE_MENU_START;
        this.y = HEIGHT - BOTTOM_PAD;
        this.amountOfItems = PlayerConfig.PLAYER_ACTIONS.size();
        this.amountOfDrawnItems = (Math.min(amountOfItems, ITEMLISTLENGTH));
        this.width = HudConstants.BATTLE_MENU_WIDTH;
        this.height = HudConstants.HUD_ROW_HEIGHT * amountOfDrawnItems / ITEMLISTLENGTH;
        this.items = PlayerConfig.PLAYER_ACTIONS;
        this.currentPosition = 0;
        this.firstDrawnItem = 0;
        this.lastDrawnItem = amountOfDrawnItems;
        this.confirmConsumer = new CopyOnWriteArrayList<>();


        setUpMenuInput(items);
    }

    private void setUpMenuInput(ArrayList<String> items) {
        Input.keyboard().onKeyTyped(e -> {
            if ((this instanceof DrawCatchMenu)) return;
            if ((this instanceof DrawAttackMenu)) return;
            if (!this.isFocused()) return;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    Game.audio().playSound("Menu_change");
                    this.decPosition();
                    if (DEBUG_CONSOLE_OUT) System.out.println("Up | " + this.currentPosition);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    Game.audio().playSound("Menu_change");
                    this.incPosition();
                    if (DEBUG_CONSOLE_OUT) System.out.println("Down | " + this.currentPosition);
                    break;
                case KeyEvent.VK_D:
                    Game.audio().playSound("Menu_pick");
                    if (DEBUG_CONSOLE_OUT) System.out.println(items.get(this.getCurrentPosition()));
                    switch (items.get(this.getCurrentPosition())) {
                        case "Attack":
                        case "Catch":
                            this.confirm();
                            break;
                    }
                    break;
            }
        });
    }

    public void onConfirm(Consumer<Integer> cons) {
        this.confirmConsumer.add(cons);
    }

    // get events from constructor
    public void confirm() {
        for (Consumer<Integer> cons : this.confirmConsumer) {
            cons.accept(this.currentPosition);
        }
    }

    public void draw(Graphics2D g) {
        this.draw(g, 0);
    }

    public void draw(Graphics2D g, int posInBattleMenu) {
        int downShift = posInBattleMenu * SUBMENUSHIFT;
        // draw background
        g.setColor(HudConstants.BACKGROUND);
        g.fillRect(x, y + downShift, width, height);

        // draw buttons
        int buttonPad = 2;

        for (int i = firstDrawnItem; i < lastDrawnItem; i++) {
            // draw buttons
            g.setColor(HudConstants.BUTTONCOLOR);
            g.fillRect(x + buttonPad,
                    y + downShift + buttonPad + height * (i - firstDrawnItem) / amountOfDrawnItems,
                    width - 2 * buttonPad,
                    height / amountOfDrawnItems - 2 * buttonPad);

            // draw selection
            g.setColor(HudConstants.SELECTCOLOR);
            if (i == currentPosition) {
                g.drawRect(x, y + downShift + height * (i - firstDrawnItem) / amountOfDrawnItems, width, height / amountOfDrawnItems);
            }

            // draw text
            g.setColor(HudConstants.TEXTCOLOR);
            g.drawString(items.get(i), x + buttonPad, (y + 20 + buttonPad + downShift) + height * (i - firstDrawnItem) / amountOfDrawnItems);
        }
    }

    public void incPosition() {
        this.currentPosition = ++this.currentPosition % (amountOfItems);
        if (this.currentPosition == 0) {
            this.firstDrawnItem = 0;
            this.lastDrawnItem = this.amountOfDrawnItems;
        } else if (this.currentPosition >= this.lastDrawnItem) {
            this.firstDrawnItem++;
            this.lastDrawnItem++;
        }
    }

    public void decPosition() {
        this.currentPosition--;
        if (this.currentPosition < 0) {
            this.currentPosition = amountOfItems - 1;
            this.lastDrawnItem = amountOfItems;
            this.firstDrawnItem = amountOfItems - amountOfDrawnItems;
            return;
        } else if (this.currentPosition < this.firstDrawnItem) {
            this.firstDrawnItem--;
            this.lastDrawnItem--;
        }
        this.currentPosition = this.currentPosition % (amountOfItems);
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getCurrentFocus() {
        return currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public boolean isFocused() {
        return this.focus;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void addItems(String item) {
        this.items.add(item);
    }

    public void setAmountOfDrawnItems(int amountOfDrawnItems) {
        this.amountOfDrawnItems = amountOfDrawnItems;
    }

    public int getAmountOfDrawnItems() {
        return amountOfDrawnItems;
    }

    public void setLastDrawnItem(int lastDrawnItem) {
        this.lastDrawnItem = lastDrawnItem;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }
}
