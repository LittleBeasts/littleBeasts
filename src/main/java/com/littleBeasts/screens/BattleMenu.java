package com.littleBeasts.screens;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class BattleMenu {
    private int x, y, width, height, currentPosition;
    private String[] items;
    private Graphics2D g;
    private boolean focus = false;
    private final List<Consumer<Integer>> confirmConsumer;

    public BattleMenu(int x, int y, int width, int height, String[] items) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.items = items;
        this.currentPosition = 0;
        this.confirmConsumer = new CopyOnWriteArrayList<>();

        Input.keyboard().onKeyTyped(e -> {
            if (!focus) return;
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                //Game.audio().playSound("Menu_change");
                decPosition();
                System.out.println("Up | " + currentPosition);
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
               // Game.audio().playSound("Menu_change");
                incPosition();
                System.out.println("Down | " + currentPosition);
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                //Game.audio().playSound("Menu_pick");
                System.out.println(items[currentPosition]);
                this.confirm();
            }
        });
    }


    public void onConfirm(Consumer<Integer> cons) {
        this.confirmConsumer.add(cons);
    }


    private void confirm() {
        for (Consumer<Integer> cons : this.confirmConsumer) {
            cons.accept(this.currentPosition);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        int buttonPad = 2;
        int i = 0;
        for (String item : items) {
            g.setColor(Color.RED);
            if (i == currentPosition) {
                g.drawRect(x, y + height * i / items.length, width, height / items.length);
            }

            g.setColor(Color.GRAY);
            g.fillRect(x + buttonPad, y + buttonPad + height * i / items.length, width - 2 * buttonPad, height / items.length - 2 * buttonPad);
            g.setColor(Color.BLACK);
            g.drawString(item, x + buttonPad, (y + 20 + buttonPad) + height * i++ / items.length);
        }
    }

    public void incPosition() {
        this.currentPosition = ++this.currentPosition % (this.items.length);
    }

    public void decPosition() {
        this.currentPosition--;
        if (this.currentPosition < 0) {
            this.currentPosition = (this.items.length - 1);
            return;
        }
        this.currentPosition = this.currentPosition % (this.items.length);
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getCurrentFocus() {
        return currentPosition;
    }

}
