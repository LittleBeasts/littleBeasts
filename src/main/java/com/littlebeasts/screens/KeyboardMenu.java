package com.littlebeasts.screens;

import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static config.HudConstants.BUTTON_BLACK;
import static config.HudConstants.BUTTON_RED;
import static config.HudConstants.HEIGHT;
import static config.HudConstants.MENU_BACKGROUND;
import static config.HudConstants.MENU_BUTTON_WIDTH;
import static config.HudConstants.MENU_CENTER_X;
import static config.HudConstants.MENU_CENTER_Y;
import static config.HudConstants.MENU_DELAY;
import static config.HudConstants.MENU_FONT;
import static config.HudConstants.MENU_FONT_COLOR;
import static config.HudConstants.WIDTH;

public class KeyboardMenu extends Menu {

    private final List<Consumer<Integer>> confirmConsumer;
    private int currentPosition;
    private long lastMenuInput;

    public KeyboardMenu(String... menuItems) {
        super(MENU_CENTER_X - MENU_BUTTON_WIDTH / 2, MENU_CENTER_Y * 1.3, MENU_BUTTON_WIDTH, MENU_CENTER_Y / 2, menuItems);
        this.confirmConsumer = new CopyOnWriteArrayList<>();

        Input.keyboard().onKeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_E) {
                if (this.menuInputIsLocked()) {
                    return;
                }
                this.confirm();
                Game.audio().playSound("Menu_pick");
                lastMenuInput = Game.time().now();
            }
        });

        Input.keyboard().onKeyPressed(e -> {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (this.menuInputIsLocked()) {
                        return;
                    }
                    Game.audio().playSound("Menu_change");
                    decPosition();
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (this.menuInputIsLocked()) {
                        return;
                    }
                    Game.audio().playSound("Menu_change");
                    incPosition();
                    break;
                default:
                    break;
            }
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
            comp.setFont(MENU_FONT);
            comp.getAppearance().setForeColor(MENU_FONT_COLOR);
            comp.getAppearance().setBackgroundColor1(BUTTON_BLACK);
            comp.getAppearanceHovered().setBackgroundColor1(BUTTON_RED);
            comp.getAppearance().setTransparentBackground(false);
            comp.getAppearanceHovered().setTransparentBackground(false);
        });
    }

    public void draw(Graphics2D g) {
        if (Program.getGameLogic().getState() == GameState.INGAME_MENU) {
            g.setColor(MENU_BACKGROUND);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
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
    }
}
