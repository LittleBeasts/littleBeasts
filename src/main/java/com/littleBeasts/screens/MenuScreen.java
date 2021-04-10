package com.littleBeasts.screens;

import client.Client;
import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

import static config.HudConstants.MAIN_MENU_ITEMS;

/*--------------------------------------------
This class creates a menu for use in battle.
It is called in the HUD class.
Height, width and bottom pad should be adjusted in HudConstants

20201105 D.B. Created Class
--------------------------------------------*/

public class MenuScreen extends Screen implements IUpdateable {

    private KeyboardMenu mainMenu;
    public long lastPlayed;
    private static final String COPYRIGHT = "2020 littleBeasts";
    private static Collection<MapArea> mapAreas;

    public MenuScreen() {
        super("MAINMENU");
    }

    private void exit() {
        System.exit(0);
    }

    @Override
    protected void initializeComponents() {
        this.mainMenu = new KeyboardMenu(MAIN_MENU_ITEMS);
        this.getComponents().add(this.mainMenu);
        this.mainMenu.onConfirm(c -> {
            switch (c) {
                case 0:
                    this.startGame();
                    break;
                case 1:
                    try {
                        this.startOnlineGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    this.exit();
                    break;
                default:
                    break;
            }
        });
    }


    @Override
    public void prepare() {
        this.mainMenu.setEnabled(true);
        super.prepare();
        Game.loop().attach(this);
        Game.window().getRenderComponent().setBackground(Color.BLACK);
        Game.graphics().setBaseRenderScale(6f * Game.window().getResolutionScale());
        this.mainMenu.incPosition();
        Game.world().loadEnvironment("Arkham");
        mapAreas = Game.world().environment().getAreas();
        Game.world().camera().setFocus(Game.world().environment().getCenter());
    }

    @Override
    public void render(final Graphics2D g) {
        Game.world().environment().render(g);
        final double centerX = Game.window().getResolution().getWidth() / 2.0;
        final double stringWidth = g.getFontMetrics().stringWidth(COPYRIGHT);
        g.setColor(Color.WHITE);
        TextRenderer.renderWithOutline(g, COPYRIGHT, centerX - stringWidth / 2, Game.window().getResolution().getHeight() * 19 / 20, Color.BLACK, true);
        super.render(g);
    }

    private void startGame() {
        this.mainMenu.setEnabled(false);
        GameLogic.setOnlineGame(false);
        Game.window().getRenderComponent().fadeOut(500);

        Game.loop().perform(100, () -> {
            Game.screens().display("INGAME-SCREEN");
            Game.world().loadEnvironment("Arkham");
            GameLogic.setState(GameState.INGAME);
        });
    }

    private void startOnlineGame() throws IOException {
        this.mainMenu.setEnabled(false);
        GameLogic.setOnlineGame(true);
        GameLogic.setClient(new Client("TestUser"));
        Game.window().getRenderComponent().fadeOut(500);

        Game.loop().perform(100, () -> {
            Game.screens().display("INGAME-SCREEN");
            Game.world().loadEnvironment("Arkham");
            GameLogic.setState(GameState.INGAME);
        });
    }

    @Override
    public void suspend() {
        super.suspend();
        Game.loop().detach(this);
        Game.audio().stopMusic();
    }

    @Override
    public void update() {
        if (this.lastPlayed == 0) {
            this.lastPlayed = Game.loop().getTicks();
        }
    }
}
