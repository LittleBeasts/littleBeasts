package com.littleBeasts.screens;

import client.Client;
import com.littleBeasts.Program;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.LitiClient;
import com.littleBeasts.gameLogic.MapNames;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.Screen;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

import static config.HudConstants.MAIN_MENU_ITEMS;

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
        LitiClient.setOnlineGame(false);
        //Game.window().getRenderComponent().fadeOut(500);
        loadUpMap();
    }

    private void startOnlineGame() throws IOException {
        this.mainMenu.setEnabled(false);
        LitiClient.setClient(new Client("TestUser"));
        LitiClient.setOnlineGame(true);
        //Game.window().getRenderComponent().fadeOut(500);
        loadUpMap();
    }

    private void loadUpMap() {
        Game.loop().perform(100, () -> {
            Game.screens().display("INGAME-SCREEN");
            if (Program.getStartingMap() != null) {
                Game.world().loadEnvironment(Program.getStartingMap());
            } else {
                Game.world().loadEnvironment(MapNames.FleaMarket.toString());
            }
            Program.getGameLogic().setState(GameState.INGAME);
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
