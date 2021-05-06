package com.littleBeasts.gameLogic;

import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.screens.DrawChatWindow;
import com.littleBeasts.screens.IngameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.io.IOException;

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
        initialiseCamera();

        Game.world().onLoaded(e -> {
            if (e.getMap().getName().equals("title")) {
                return;
            }
            currentLitiMap.newMapLoadUp();


            Spawnpoint enter = e.getSpawnpoint("debug");
            if (enter != null) {
                enter.spawn(LitiPlayer.instance());
                enter.spawn(LitiPet.instance());
                LitiPet.instance().setX(LitiPet.instance().getX() + 16);
            }
        });
    }

    private void initialiseCamera() {
        camera = new PositionLockCamera(LitiPlayer.instance());
        camera.setClampToMap(true); // Camara stop at edge of map.
        camera.setZoom(1.0f, 0);
        Game.world().setCamera(camera);
    }

    public GameState getState() {
        return GameLogic.state;
    }

    public void setState(GameState state) {
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
        currentLitiMap.update();
        LitiBattle.update();
        LitiClient.update();
    }

    public static LitiMap getCurrentLitiMap() {
        return currentLitiMap;
    }
}