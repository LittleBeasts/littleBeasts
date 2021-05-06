package com.littleBeasts.screens;

import com.littleBeasts.Program;
import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;

public class IngameScreen extends Screen {
    public static final String NAME = "INGAME-SCREEN";
    public static KeyboardMenu ingameMenu;
    public static DrawChatWindow drawChatWindow;
    public static DrawInventory inventory;
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
                    Program.getGameLogic().setState(GameState.MENU);
                    break;
                case 2:
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
}
