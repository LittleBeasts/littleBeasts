package com.littleBeasts.gameLogic;

import com.littleBeasts.entities.LitiPet;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.guiComponent.DrawChatWindow;
import com.littleBeasts.sceneManager.SceneNotPossibleError;
import com.littleBeasts.screens.IngameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class GameLogic implements IUpdateable {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;
    private static Camera camera;
    private static LitiMap currentLitiMap;

    public void init() {
        Game.loop().attach(this);
        currentLitiMap = new LitiMap();
        initialiseCamera();

        Game.world().onLoaded(e -> {
            if (e.getMap().getName().equals("title")) {
                return;
            }
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
        Game.world().setCamera(camera);
        Game.world().camera().setZoom(1, 500);
        GameLogic.state = state;
        LitiPlayer.instance().setState(PlayerState.LOCKED);
        Game.loop().setTimeScale(1);
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
                Game.audio().playMusic("battle");
                LitiBattle.triggerBattle();
                break;
            case INGAME:
                firstStart = false;
                LitiPlayer.instance().setState(PlayerState.CONTROLLABLE);
                IngameScreen.getDrawChatWindow().setVisible(false);
                IngameScreen.getIngameMenu().setVisible(false);
                Game.audio().playMusic("arkham");
                break;
            case INGAME_MENU:
                Game.loop().setTimeScale(0);
                IngameScreen.getIngameMenu().setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
            case SAVE_MENU:
                break;
            case INGAME_CHAT:
                IngameScreen.getDrawChatWindow().setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
            case INVENTORY:
                Game.loop().setTimeScale(0);
                IngameScreen.getInventory().setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
        }
        if (DEBUG_CONSOLE_OUT) System.out.println(GameLogic.state.name());
    }

    @Override
    public void update() {
        try {
            currentLitiMap.update();
        } catch (SceneNotPossibleError sceneNotPossibleError) {
            sceneNotPossibleError.printStackTrace();
        }
        LitiBattle.update();
        LitiClient.update();
    }

    public LitiMap getCurrentLitiMap() {
        return currentLitiMap;
    }

}