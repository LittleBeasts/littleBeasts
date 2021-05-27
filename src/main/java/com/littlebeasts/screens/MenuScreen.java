package com.littlebeasts.screens;

import client.Client;
import com.littlebeasts.Program;
import com.littlebeasts.gamelogic.GameLogic;
import com.littlebeasts.gamelogic.GameState;
import com.littlebeasts.gamelogic.LitiClient;
import com.littlebeasts.gamelogic.MapNames;
import com.littlebeasts.guicomponent.SaveMenu;
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
    private SaveMenu saveMenu;
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
        this.saveMenu = new SaveMenu(GameState.MENU);
        this.getComponents().add(this.mainMenu);
        this.getComponents().add(this.saveMenu);
        this.mainMenu.onConfirm(c -> {
            switch (c) {
                case 0:
                    mainMenu.setVisible(false);
                    saveMenu.setFocus(true);
                    Program.getGameLogic().setState(GameState.SAVE_MENU);
                    saveMenu.setVisible(true);
                    break;
                case 1:
                    this.startLocalGame();
                    break;
                case 2:
                    try {
                        this.startOnlineGame();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
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

    private void startLocalGame() {
        this.mainMenu.setEnabled(false);
        LitiClient.setOnlineGame(false);
        loadUpMap();
    }

    private void startOnlineGame() throws IOException {
        this.mainMenu.setEnabled(false);
        LitiClient.setClient(new Client("TestUser"));
        LitiClient.setOnlineGame(true);
        loadUpMap();
    }

    private void loadUpMap() {
        Game.loop().setTimeScale(1);
        Game.loop().perform(100, () -> {
            Game.screens().display("INGAME-SCREEN");
            if (Program.getStartingMap() != null) {
                Game.world().loadEnvironment(Program.getStartingMap());
                Program.getGameLogic().getCurrentLitiMap().newMapLoadUp();
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

    public KeyboardMenu getMainMenu() {
        return mainMenu;
    }

    public SaveMenu getSaveMenu() {
        return saveMenu;
    }
}
