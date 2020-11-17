package com.littleBeasts;

import calculationEngine.battle.Battle;
import calculationEngine.entities.Beasts;
import calculationEngine.entities.CeAi;
import calculationEngine.entities.CePlayer;
import client.Client;
import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.screens.DrawChatWindow;
import com.littleBeasts.screens.IngameScreen;
import config.TestConfig;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class GameLogic implements IUpdateable {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;
    public static String START_LEVEL = "Arkham";

    private static final List<LitiBeast> LITI_BEAST_LIST = new ArrayList<>(); // list to resolve all animation before removing entity TODO: find a way to finish animation w/o this list.
    private static Camera camera;
    private static Battle battle;
    private static CePlayer cePlayer;
    private static boolean nextBattlePossible = true;

    private static Client client;
    private static List<String> bufferedMessages;

    private static boolean onlineGame;

    public GameLogic() {

    }

    /**
     * Initializes the game logic for the game.
     */
    public void init() {
        Game.loop().attach(this);
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

            // spawn the player instance on the spawn point with the name "west"
            Spawnpoint enter = e.getSpawnpoint("west");
            if (enter != null) {
                enter.spawn(LitiPlayer.instance());
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
                nextBattlePossible = false;
                LitiPlayer.instance().detachControllers();
                Game.audio().playMusic("battle");
                triggerBattle();
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

    private static void triggerBattle() {
        int x = 0;
        boolean faceLeft = false;
        if (LitiPlayer.instance().getFacingDirection() == Direction.LEFT) {
            x = (int) LitiPlayer.instance().getX() - 50;
            faceLeft = true;
        } else {
            x = (int) LitiPlayer.instance().getX() + 50;
        }
        Camera battleCam = new Camera();
        battleCam.setClampToMap(false);
        Point2D point2D = Game.world().camera().getViewportLocation(LitiPlayer.instance());
        Game.world().setCamera(battleCam);
        Game.world().camera().setZoom(1.5f, 500);
        Game.world().camera().setFocus(LitiPlayer.instance().getX() + (faceLeft ? -25 : 25), LitiPlayer.instance().getY());

        //for dev purposes
        LitiBeast litiBeast = new LitiBeast(Beasts.FeuerFurz, x, (int) (LitiPlayer.instance().getY() - (LitiPlayer.instance().getHeight() / 2)), false);
        litiBeast.setFacingDirection(LitiPlayer.instance().getFacingDirection().getOpposite());
        LITI_BEAST_LIST.add(litiBeast);

        cePlayer = LitiPlayer.instance().getCePlayer();
        CeAi ai = new CeAi(cePlayer, litiBeast.getCeEntity());
        battle = new Battle(LitiPlayer.instance().getCePlayer(), ai);
        LitiPlayer.instance().setBattle(battle);
        LitiPlayer.instance().setIsFighting(true);
    }

    @Override
    public void update() {
        loadNewArea();
        startBattle();
        if (getState() == GameState.INGAME) {
            for (int i = 0; i < LITI_BEAST_LIST.size(); i++) {
                if (LITI_BEAST_LIST.get(i).getBeastStats().isReadyToBeRemoved()) {
                    LITI_BEAST_LIST.get(i).die();
                    Game.world().environment().remove(LITI_BEAST_LIST.get(i));
                    LITI_BEAST_LIST.remove(i);
                }
                if (LITI_BEAST_LIST.size() == 0) {
                    nextBattlePossible = true;
                }
            }
        }
        if (isOnlineGame()) {
            readBufferedMessages();
        }

    }

    public void readBufferedMessages() {
        if (client.getClientListener().messagesBuffered()) {
            if (DEBUG_CONSOLE_OUT)  System.out.println("buffered Messages");
            bufferedMessages = client.getClientListener().getMessageBuffer();
        }
    }

    public void loadNewArea() {
        Collection<MapArea> areas = Game.world().environment().getAreas();
        Point2D playerPosition;
        Rectangle2D mapArea;
        for (MapArea area : areas) {
            mapArea = area.getBoundingBox();
            playerPosition = LitiPlayer.instance().getCenter();
            playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
            if (mapArea.contains(playerPosition)) {
                String originName = Game.world().environment().getMap().getName();
                if (DEBUG_CONSOLE_OUT)    System.out.println(area.getName());
                Game.world().loadEnvironment(area.getName());
                Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(originName);
                if (spawnpoint != null) {
                    spawnpoint.spawn(LitiPlayer.instance());
                }
                LitiPlayer.instance().setFacingDirection(Direction.DOWN);
                LitiPlayer.instance().setRenderWithLayer(true);
            }
        }
    }

    public void startBattle() {
        if (LitiPlayer.instance().isFighting()) {
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == cePlayer.getNumber()) {

                }
            } else {
                if (DEBUG_CONSOLE_OUT)  System.out.println("End of fight");
                setState(GameState.INGAME);
            }
        }
    }

    public static void robotButtonPress(int i) {
        try {
            Robot robert = new Robot();
            Thread.sleep(TestConfig.ROBOT_SLEEP);
            robert.keyPress(i);
            Thread.sleep(100);
            robert.keyRelease(i);
            Thread.sleep(TestConfig.ROBOT_SLEEP);
        } catch (AWTException awtException) {
            awtException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Battle getBattle() {
        return battle;
    }

    public static List<LitiBeast> getBeastList() {
        return LITI_BEAST_LIST;
    }

    public static boolean isNextBattlePossible() {
        return nextBattlePossible;

    }

    public static void sendMessageToServer(String message) throws IOException {
        client.sendMessage(message);
    }

    public static List<String> getBufferedMessages() {
        if (bufferedMessages == null)
            return null;
        List<String> tmp = new ArrayList<>(bufferedMessages);
        bufferedMessages.clear();
        return tmp;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        GameLogic.client = client;
    }

    public static boolean isOnlineGame() {
        return onlineGame;
    }

    public static void setOnlineGame(boolean onlineGame) {
        GameLogic.onlineGame = onlineGame;
    }

    public void returnToMainMenu() {
        robotButtonPress(KeyEvent.VK_ESCAPE);
        robotButtonPress(KeyEvent.VK_DOWN);
        robotButtonPress(KeyEvent.VK_ENTER);
    }
}





