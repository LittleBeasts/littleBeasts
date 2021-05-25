package com.littlebeasts.screens;

import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameState;
import com.littlebeasts.guicomponent.DrawChatWindow;
import com.littlebeasts.guicomponent.DrawHud;
import com.littlebeasts.guicomponent.DrawInventory;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;

public class IngameScreen extends Screen {
    public static final String NAME = "INGAME-SCREEN";
    private static KeyboardMenu ingameMenu;
    private static DrawChatWindow drawChatWindow;
    private static DrawInventory inventory;
    private DrawHud drawHud;

    public IngameScreen() {
        super(NAME);
    }

    @Override
    public void prepare() {
        super.prepare();
    }

    @Override
    public void suspend() {
        super.suspend();
        Game.audio().stopMusic();
    }

    protected void initializeComponents() {
        this.drawHud = new DrawHud();
        this.drawHud = new DrawHud();
        drawChatWindow = new DrawChatWindow();
        inventory = new DrawInventory();
        buildIngameMenu();
        this.getComponents().add(ingameMenu);
        this.getComponents().add(drawChatWindow);
        this.getComponents().add(inventory);
        this.getComponents().add(this.drawHud);
    }

    private void buildIngameMenu() {
        ingameMenu = new KeyboardMenu(HudConstants.INGAME_MENU_ITEMS);
        ingameMenu.onConfirm(c -> {
            switch (c) {
                case 0:
                    Program.getGameLogic().setState(GameState.INGAME);
                    break;
                case 1:
                    Program.getGameLogic().setState(GameState.SAVE_MENU);
                    break;
                case 2:
                    Program.getGameLogic().setState(GameState.MENU);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void render(Graphics2D g) {
        if (Game.world().environment() != null) {
            Game.world().environment().render(g);
        }
        ingameMenu.draw(g);
        super.render(g);
    }

    public DrawHud getHud() {
        return drawHud;
    }

    public static DrawInventory getInventory() {
        return inventory;
    }

    public static DrawChatWindow getDrawChatWindow() {
        return drawChatWindow;
    }

    public static KeyboardMenu getIngameMenu() {
        return ingameMenu;
    }
}
