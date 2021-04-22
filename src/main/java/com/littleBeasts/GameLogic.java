package com.littleBeasts;

import calculationEngine.battle.CeBattle;
import calculationEngine.entities.CeAi;
import calculationEngine.entities.CeBeasts;
import calculationEngine.entities.CePlayer;
import client.Client;
import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiInteractable;
import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.screens.DrawChatWindow;
import com.littleBeasts.screens.IngameScreen;
import config.TestConfig;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.entities.behavior.AStarGrid;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.environment.tilemap.ITileLayer;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class GameLogic implements IUpdateable {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;
    private static Camera camera;
    private static LitiMap currentLitiMap;

    public void init() throws IOException, FontFormatException {
        Game.loop().attach(this);
        currentLitiMap = new LitiMap();
        LitiMap.loadFonts();
        //  Environment.registerMapObjectLoader(new CustomMapObjectLoader());

        // we'll use a camera in our game that is locked to the location of the player
        camera = new PositionLockCamera(LitiPlayer.instance());
        camera.setClampToMap(true); // Camara stop at edge of map.
        camera.setZoom(1.0f, 0);
        Game.world().setCamera(camera);

        Game.world().onLoaded(e -> {
            if (e.getMap().getName().equals("title")) {
                return;
            }

            Game.loop().perform(500, () -> Game.window().getRenderComponent().fadeIn(0));

            LitiPlayer.instance().setIndestructible(false);
            LitiPlayer.instance().setCollision(true);

            LitiPet.instance().setIndestructible(false);
            LitiPet.instance().setCollision(false);

            currentLitiMap.newMapLoadUp();

            // spawn the player instance on the spawn point with the name "west"
            Spawnpoint enter = e.getSpawnpoint("west");
            if (enter != null) {
                enter.spawn(LitiPlayer.instance());
                enter.spawn(LitiPet.instance());
            }
        });
    }

    public static GameState getState() {
        return GameLogic.state;
    }

    public static void setState(GameState state) {
        Game.audio().stopMusic();
        Game.world().setCamera(camera);
        Game.world().camera().setZoom(1, 500);
        GameLogic.state = state;
        Game.loop().setTimeScale(1);
        LitiPlayer.instance().attachControllers();
        LitiPlayer.instance().movement().attach();
        LitiPlayer.instance().setIsFighting(false);
        Input.keyboard().onKeyTyped(DrawChatWindow::add);

        switch (state) {
            case MENU:
                if (!firstStart) {
                    Game.loop().setTimeScale(0);
                    Game.screens().display("MAINMENU");
                    Game.audio().playMusic("titlemenu");
                }
                break;
            case BATTLE:
                LitiBattle.setNextBattlePossible(false);
                LitiPlayer.instance().detachControllers();
                Game.audio().playMusic("battle");
                LitiBattle.triggerBattle();
                break;
            case INGAME:
                firstStart = false;
                IngameScreen.drawChatWindow.setVisible(false);
                IngameScreen.ingameMenu.setVisible(false);
                Game.audio().playMusic("arkham");
                break;
            case INGAME_MENU:
                Game.loop().setTimeScale(0);
                LitiPlayer.instance().detachControllers();
                IngameScreen.ingameMenu.setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
            case INGAME_CHAT:
                LitiPlayer.instance().detachControllers();
                IngameScreen.drawChatWindow.setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
            case INVENTORY:
                Game.loop().setTimeScale(0);
                LitiPlayer.instance().detachControllers();
                IngameScreen.inventory.setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
        }
        if (DEBUG_CONSOLE_OUT) System.out.println(GameLogic.state.name());
    }

    @Override
    public void update() {
        currentLitiMap.loadNewArea();
        LitiBattle.startBattle();
        currentLitiMap.checkOpacity();
        LitiBattle.removeBeast();
        if (LitiClient.isOnlineGame()) {
            LitiClient.readBufferedMessages();
        }
    }

    public static LitiMap getCurrentLitiMap() {
        return currentLitiMap;
    }
}