package com.littleBeasts.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import com.littleBeasts.GameLogic;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.input.Input;

public class KeyboardMenu extends Menu {

    //public static final Color ROMAN_RED = new Color(140, 16, 16);
    public static final Color BUTTON_RED = new Color(140, 16, 16, 200);
    public static final Color BUTTON_BLACK = new Color(0, 0, 0, 200);
    //public static final Sound SETTING_CHANGE_SOUND = Resources.sounds().get("swing1.ogg");
    public static final int MENU_DELAY = 180;

    private final List<Consumer<Integer>> confirmConsumer;
    protected int currentPosition = -1;

    public static long lastMenuInput;

    public KeyboardMenu(double x, double y, double width, double height, String... items) {
        super(x, y, width, height, items);
        this.confirmConsumer = new CopyOnWriteArrayList<>();

        Input.keyboard().onKeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_E) {
                if (this.menuInputIsLocked()) {
                    return;
                }

                this.confirm();
                //Game.audio().playSound("Menu_pick");
                lastMenuInput = Game.time().now();
            }
        });

        Input.keyboard().onKeyPressed(KeyEvent.VK_UP, e -> {
            if (this.menuInputIsLocked()) {
                return;
            }
           // Game.audio().playSound("Menu_change");
            decPosition();
        });

        Input.keyboard().onKeyPressed(KeyEvent.VK_DOWN, e -> {

            if (this.menuInputIsLocked()) {
                return;
            }
           // Game.audio().playSound("Menu_change");
            incPosition();
        });

    }

    private boolean menuInputIsLocked() {
        // disable menu if the game has started
        if (this.isSuspended() || !this.isVisible() || !this.isEnabled()) {
            return true;
        }
        return Game.time().since(lastMenuInput) < MENU_DELAY;
    }

    @Override
    public void prepare() {
        super.prepare();
        this.setForwardMouseEvents(false);
        this.getCellComponents().forEach(comp -> comp.setForwardMouseEvents(false));

        if (!this.getCellComponents().isEmpty()) {
            this.currentPosition = 0;
            this.getCellComponents().get(0).setHovered(true);
        }

        this.getCellComponents().forEach(comp -> {
            comp.setFont(GameLogic.MENU_FONT);
            comp.getAppearance().setForeColor(Color.WHITE);
            comp.getAppearance().setBackgroundColor1(BUTTON_BLACK);
            comp.getAppearanceHovered().setBackgroundColor1(BUTTON_RED);
            comp.getAppearance().setTransparentBackground(false);
            comp.getAppearanceHovered().setTransparentBackground(false);
            //comp.getAppearance().setTextAntialiasing(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            //comp.getAppearanceHovered().setTextAntialiasing(RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
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

    protected void decPosition() {
        this.currentPosition = Math.floorMod(--this.currentPosition, this.getCellComponents().size());
        this.updatePosition();
    }

    protected void incPosition() {
        this.currentPosition = ++this.currentPosition % this.getCellComponents().size();
        this.updatePosition();
    }

    protected void updatePosition() {
        this.setCurrentSelection(this.currentPosition);
        for (int i = 0; i < this.getCellComponents().size(); i++) {
            this.getCellComponents().get(i).setHovered(i == this.currentPosition);
        }

        lastMenuInput = Game.time().now();

        if (this.isVisible()) {
            //Game.audio().playSound(SETTING_CHANGE_SOUND);
        }
    }
}
