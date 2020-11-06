package com.littleBeasts.screens;

import calculationEngine.entities.Attack;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/*--------------------------------------------
This class creates a menu for use in battle.
It is called in the HUD class.
Height, width and bottom pad should be adjusted in HudConstants

20201105 D.B. Created Class
--------------------------------------------*/

public class BattleMenu { // dev constructor
    private final int x, y, width, height, amountOfItems, amountOfDrawnItems;
    private int firstDrawnItem, lastDrawnItem, currentPosition;
    private String[] items;
    private boolean focus = false;
    private final List<Consumer<Integer>> confirmConsumer;

    public BattleMenu(int x, String[] items) { // for dev purposes
        this.x = x; //position left
        this.y = HudConstants.HEIGHT - HudConstants.BOTTOM_PAD;
        this.amountOfItems = items.length;
        this.amountOfDrawnItems = (Math.min(amountOfItems, HudConstants.ITEMLISTLENGTH));
        this.width = HudConstants.BATTLE_MENU_WIDTH;
        this.height = HudConstants.HUD_ROW_HEIGHT * amountOfDrawnItems / HudConstants.ITEMLISTLENGTH;
        this.items = items;
        this.currentPosition = 0;
        this.firstDrawnItem = 0;
        this.lastDrawnItem = amountOfDrawnItems;

        this.confirmConsumer = new CopyOnWriteArrayList<>();

        Input.keyboard().onKeyTyped(e -> {
            if (!focus) return;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    Game.audio().playSound("Menu_change");
                    decPosition();
                    System.out.println("Up | " + currentPosition);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    Game.audio().playSound("Menu_change");
                    incPosition();
                    System.out.println("Down | " + currentPosition);
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_D:
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_E:
                    Game.audio().playSound("Menu_pick");
                    System.out.println(items[currentPosition]);
                    this.confirm();
                    break;
            }
        });
    }

    public BattleMenu(int x, Attack[] attacks) {
        this.x = x; //position left
        this.y = HudConstants.HEIGHT - HudConstants.BOTTOM_PAD;
        this.amountOfItems = attacks.length;
        this.amountOfDrawnItems = (Math.min(amountOfItems, HudConstants.ITEMLISTLENGTH));
        this.width = HudConstants.BATTLE_MENU_WIDTH;
        this.height = HudConstants.HUD_ROW_HEIGHT * amountOfDrawnItems / HudConstants.ITEMLISTLENGTH;
        this.setItemsFromAttacks(attacks);
        this.currentPosition = 0;
        this.firstDrawnItem = 0;
        this.lastDrawnItem = amountOfDrawnItems;

        this.confirmConsumer = new CopyOnWriteArrayList<>();

        Input.keyboard().onKeyTyped(e -> {
            if (!focus) return;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    Game.audio().playSound("Menu_change");
                    decPosition();
                    System.out.println("Up | " + currentPosition);
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    Game.audio().playSound("Menu_change");
                    incPosition();
                    System.out.println("Down | " + currentPosition);
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_D:
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_E:
                    Game.audio().playSound("Menu_pick");
                    System.out.println(items[currentPosition]);
                    this.confirm();
                    break;
            }
        });
    }


    public void onConfirm(Consumer<Integer> cons) {
        this.confirmConsumer.add(cons);
    }

    // get events from constructor
    private void confirm() {
        for (Consumer<Integer> cons : this.confirmConsumer) {
            cons.accept(this.currentPosition);
        }
    }

    public void draw(Graphics2D g) {
        // draw background
        g.setColor(HudConstants.BACKGROUND);
        g.fillRect(x, y, width, height);

        // draw buttons
        int buttonPad = 2;

        for (int i = firstDrawnItem; i < lastDrawnItem; i++) {
            // draw buttons
            g.setColor(HudConstants.BUTTONCOLOR);
            g.fillRect(x + buttonPad,
                    y + buttonPad + height * (i - firstDrawnItem) / amountOfDrawnItems,
                    width - 2 * buttonPad,
                    height / amountOfDrawnItems - 2 * buttonPad);

            // draw selection
            g.setColor(HudConstants.SELECTCOLOR);
            if (i == currentPosition) {
                g.drawRect(x, y + height * (i - firstDrawnItem) / amountOfDrawnItems, width, height / amountOfDrawnItems);
            }

            // draw text
            g.setColor(HudConstants.TEXTCOLOR);
            g.drawString(items[i], x + buttonPad, (y + 20 + buttonPad) + height * (i - firstDrawnItem) / amountOfDrawnItems);
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

    public void setItemsFromAttacks(Attack[] attacks) {
        items = new String[attacks.length];
        for (int i = 0; i < attacks.length; i++) {
            items[0] = attacks[i].getName();
        }
    }

}
