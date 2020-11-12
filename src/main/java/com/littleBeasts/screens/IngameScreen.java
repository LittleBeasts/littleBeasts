package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;

// ToDo: Check if IUpdateable unnecessary
public class IngameScreen extends Screen implements IUpdateable {
    public static final String NAME = "INGAME-SCREEN";
    public static KeyboardMenu ingameMenu;
    public static ChatWindow chatWindow;
    private Hud hud;

    public IngameScreen() {
        super(NAME);
    }

    @Override
    public void prepare() {
        super.prepare();
        Game.loop().attach(this);

    }

    @Override
    public void suspend() {
        super.suspend();
        Game.loop().detach(this);
        Game.audio().stopMusic();
    }


    protected void initializeComponents() {
        this.hud = new Hud();
        chatWindow = new ChatWindow();

        final double centerX = Game.window().getResolution().getWidth() / 2.0;
        final double centerY = Game.window().getResolution().getHeight() * 1 / 2;
        final double buttonWidth = 450;

        // ToDo: extract to own Method for ingameMenu
        // ToDo: Extract Items array to Hud Config
        ingameMenu = new KeyboardMenu(centerX - buttonWidth / 2, centerY * 1.3, buttonWidth, centerY / 2, "Continue", "To Main Menu", "Exit");
        ingameMenu.onConfirm(c -> {
            switch (c) {
                case 0:
                    GameLogic.setState(GameState.INGAME);
                    break;
                case 1:
                    GameLogic.setState(GameState.MENU);
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });
        // -------------------------------------------------------------------------------------------------------------
        ChatWindow.init();
        this.getComponents().add(this.hud);
        this.getComponents().add(ingameMenu);
        this.getComponents().add(chatWindow);
    }

    @Override
    public void render(Graphics2D g) {

        if (Game.world().environment() != null) {
            Game.world().environment().render(g);
        }

        // ToDo: extract to ingame Menu
        if (GameLogic.getState() == GameState.INGAME_MENU) {
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, (int) Game.window().getResolution().getWidth(), (int) Game.window().getResolution().getHeight());
        }

        super.render(g);
    }

    public Hud getHud() {
        return hud;
    }

    // ToDo: Belongs to IUpdatable stuff
    @Override
    public void update() {
    }
}
