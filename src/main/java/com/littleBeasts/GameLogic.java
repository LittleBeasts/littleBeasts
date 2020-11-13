package com.littleBeasts;

import calculationEngine.battle.Battle;
import calculationEngine.entities.Beasts;
import calculationEngine.entities.CeAi;
import calculationEngine.entities.CePlayer;
import client.Client;
import com.littleBeasts.entities.Beast;
import com.littleBeasts.entities.Player;
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

// ToDo: try to make class Static
public class GameLogic implements IUpdateable {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;
    public static String START_LEVEL = "Arkham";

    private static final List<Beast> beastList = new ArrayList<>(); // list to resolve all animation before removing entity TODO: find a way to finish animation w/o this list.
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
    public void init() throws IOException {
        Game.loop().attach(this);
        //  Environment.registerMapObjectLoader(new CustomMapObjectLoader());

        // we'll use a camera in our game that is locked to the location of the player
        camera = new PositionLockCamera(Player.instance());
        camera.setClampToMap(true); // Camara stop at edge of map.
        camera.setZoom(1.0f, 0);
        Game.world().setCamera(camera);

        Game.world().onLoaded(e -> {
            if (e.getMap().getName().equals("title")) {
                return;
            }

            Game.loop().perform(500, () -> Game.window().getRenderComponent().fadeIn(0));

            Player.instance().setIndestructible(false);
            Player.instance().setCollision(true);

            // spawn the player instance on the spawn point with the name "west"
            Spawnpoint enter = e.getSpawnpoint("west");
            if (enter != null) {
                enter.spawn(Player.instance());
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
        Player.instance().attachControllers();
        Player.instance().movement().attach();
        Player.instance().setIsFighting(false);
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
                Player.instance().detachControllers();
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
                Player.instance().detachControllers();
                IngameScreen.ingameMenu.setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
            case INGAME_CHAT:
                Player.instance().detachControllers();
                IngameScreen.drawChatWindow.setVisible(true);
                Game.audio().playMusic("ingameMenu");
                break;
        }
        System.out.println(GameLogic.state.name());
    }

    private static void triggerBattle() {
        int x = 0;
        boolean faceLeft = false;
        if (Player.instance().getFacingDirection() == Direction.LEFT) {
            x = (int) Player.instance().getX() - 50;
            faceLeft = true;
        } else {
            x = (int) Player.instance().getX() + 50;
        }
        Camera battleCam = new Camera();
        battleCam.setClampToMap(false);
        Point2D point2D = Game.world().camera().getViewportLocation(Player.instance());
        Game.world().setCamera(battleCam);
        Game.world().camera().setZoom(1.5f, 500);
        Game.world().camera().setFocus(Player.instance().getX() + (faceLeft ? -25 : 25), Player.instance().getY());

        //for dev purposes
        Beast beast = new Beast(Beasts.FeuerFurz, x, (int) (Player.instance().getY() - (Player.instance().getHeight() / 2)), false);
        beast.setFacingDirection(Player.instance().getFacingDirection().getOpposite());
        beastList.add(beast);

        cePlayer = Player.instance().getCePlayer();
        CeAi ai = new CeAi(cePlayer, beast.getCeEntity());
        battle = new Battle(Player.instance().getCePlayer(), ai);
        Player.instance().setBattle(battle);
        Player.instance().setIsFighting(true);
    }

    @Override
    public void update() {
        loadNewArea();
        startBattle();
        if (getState() == GameState.INGAME) {
            for (int i = 0; i < beastList.size(); i++) {
                if (beastList.get(i).getBeastStats().isReadyToBeRemoved()) {
                    beastList.get(i).die();
                    Game.world().environment().remove(beastList.get(i));
                    beastList.remove(i);
                }
                if (beastList.size() == 0) {
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
            System.out.println("buffered Messages");
            bufferedMessages = client.getClientListener().getMessageBuffer();
        }
    }

    public void loadNewArea() {
        Collection<MapArea> areas = Game.world().environment().getAreas();
        Point2D playerPosition;
        Rectangle2D mapArea;
        for (MapArea area : areas) {
            mapArea = area.getBoundingBox();
            playerPosition = Player.instance().getCenter();
            playerPosition.setLocation(playerPosition.getX(), playerPosition.getY() + 12);
            if (mapArea.contains(playerPosition)) {
                String originName = Game.world().environment().getMap().getName();
                System.out.println(area.getName());
                Game.world().loadEnvironment(area.getName());
                Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(originName);
                if (spawnpoint != null) {
                    spawnpoint.spawn(Player.instance());
                }
                Player.instance().setFacingDirection(Direction.DOWN);
                Player.instance().setRenderWithLayer(true);
            }
        }
    }

    public void startBattle() {
        if (Player.instance().isFighting()) {
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == cePlayer.getNumber()) {

                }
            } else {
                System.out.println("End of fight");
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

    public static List<Beast> getBeastList() {
        return beastList;
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





