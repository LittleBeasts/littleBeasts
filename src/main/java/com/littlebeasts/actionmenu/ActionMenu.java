package com.littlebeasts.actionmenu;

import calculationEngine.environment.CeItem;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static config.HudConstants.*;

public abstract class ActionMenu {
    protected final int y;
    protected final int width;
    protected int x;
    protected int amountOfItems;
    protected int amountOfDrawnItems;
    protected int height;
    protected int firstDrawnItem;
    protected int lastDrawnItem;
    protected int currentPosition;
    protected boolean focus;
    protected ArrayList<String> items;
    private final List<Consumer<Integer>> confirmConsumer;
    private final Consumer<Boolean> menuChange;

    public ActionMenu(ArrayList<String> items) {
        this.items = items;
        this.amountOfItems = this.items.size();
        this.amountOfDrawnItems = (Math.min(amountOfItems, ITEMLISTLENGTH));
        this.currentPosition = 0;
        this.firstDrawnItem = 0;
        this.lastDrawnItem = amountOfDrawnItems;
        this.confirmConsumer = new CopyOnWriteArrayList<>();
        this.menuChange = this::setFocus;
        setX();
        this.y = HEIGHT - BOTTOM_PAD;
        this.width = BATTLE_MENU_WIDTH;
        this.height = HUD_ROW_HEIGHT * amountOfDrawnItems / ITEMLISTLENGTH;
    }

    public void draw(Graphics2D g) {
        this.draw(g, 0);
    }

    public void draw(Graphics2D g, int posInBattleMenu) {
        int downShift = posInBattleMenu * SUBMENUSHIFT;
        // draw background
        g.setColor(BACKGROUND);
        g.fillRect(x, y + downShift, width, height);

        // draw buttons
        int buttonPad = 2;

        for (int i = firstDrawnItem; i < lastDrawnItem; i++) {
            // draw buttons
            g.setColor(BUTTONCOLOR);
            g.fillRect(x + buttonPad,
                    y + downShift + buttonPad + height * (i - firstDrawnItem) / amountOfDrawnItems,
                    width - 2 * buttonPad,
                    height / amountOfDrawnItems - 2 * buttonPad);

            // draw selection
            g.setColor(SELECTCOLOR);
            if (i == currentPosition) {
                g.drawRect(x, y + downShift + height * (i - firstDrawnItem) / amountOfDrawnItems, width, height / amountOfDrawnItems);
            }

            // draw text
            g.setColor(TEXTCOLOR);
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

    public boolean isFocused() {
        return this.focus;
    }

    public void onConfirm(Consumer<Integer> cons) {
        this.confirmConsumer.add(cons);
    }

    public Consumer<Boolean> getMenuChange() {
        return menuChange;
    }

    public void confirm() {
        for (Consumer<Integer> cons : this.confirmConsumer) {
            cons.accept(this.currentPosition);
        }
    }

    public static ArrayList<String> getItemsNames(ArrayList<CeItem> ceItems) {
        ArrayList<String> itemsNames = new ArrayList<>();
        for (CeItem ceItem : ceItems) {
            if (ceItem != null)
                itemsNames.add(ceItem.getName());
        }
        if (itemsNames.isEmpty())
            itemsNames.add(NO_ITEMS_PLACEHOLDER);
        return itemsNames;
    }

    protected abstract void setX();
}
