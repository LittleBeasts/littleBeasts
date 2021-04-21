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
    public static String START_LEVEL = "Arkham";

    private static final List<LitiBeast> LITI_BEAST_LIST = new ArrayList<>(); // list to resolve all animation before removing entity TODO: find a way to finish animation w/o this list.
    private static Camera camera;
    private static CeBattle battle;
    private static CePlayer cePlayer;
    private static boolean nextBattlePossible = true;

    private static Client client;
    private static List<String> bufferedMessages;

    private static boolean onlineGame;
    private static AStarPathFinder currentPathFinder;
    private static AStarGrid currentGrid;
    private static ArrayList<LitiInteractable> litiInteractables;
    private static ArrayList<Font> gameFonts = new ArrayList<>();

    public GameLogic() {

    }

    /**
     * Initializes the game logic for the game.
     */
    public void init() throws IOException, FontFormatException {
        Game.loop().attach(this);
        loadFonts();
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

            createInteractableList();

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
        LitiBeast litiBeast = new LitiBeast(CeBeasts.FeuerFurz, x, (int) (LitiPlayer.instance().getY() - (LitiPlayer.instance().getHeight() / 2)), false);
        litiBeast.setFacingDirection(LitiPlayer.instance().getFacingDirection().getOpposite());
        LITI_BEAST_LIST.add(litiBeast);

        cePlayer = LitiPlayer.instance().getCePlayer();
        CeAi ai = new CeAi(litiBeast.getCeEntity());
        battle = new CeBattle(LitiPlayer.instance().getCePlayer(), ai);
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
            if (DEBUG_CONSOLE_OUT) System.out.println("buffered Messages");
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
                if (area.getName().contains("AREA-")) {
                    String originName = Game.world().environment().getMap().getName();
                    if (DEBUG_CONSOLE_OUT) System.out.println(area.getName().replace("AREA-", ""));
                    Game.world().loadEnvironment(area.getName().replace("AREA-", ""));
                    Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint(originName);
                    if (spawnpoint != null) {
                        spawnpoint.spawn(LitiPlayer.instance());
                    }
                    LitiPlayer.instance().setFacingDirection(Direction.DOWN);
                    LitiPlayer.instance().setRenderWithLayer(true);
                    createInteractableList();
                }
            }
        }
    }

    public void startBattle() {
        if (LitiPlayer.instance().isFighting()) {
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == cePlayer.getNumber()) {

                }
            } else {
                if (DEBUG_CONSOLE_OUT) System.out.println("End of fight");
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

    public static CeBattle getBattle() {
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

    public static AStarPathFinder getCurrentPathFinder() {
        return currentPathFinder;
    }

    public static AStarGrid getCurrentGrid() {
        return currentGrid;
    }

    public void createInteractableList() {
        litiInteractables = new ArrayList<>();
        Collection<IEntity> collectionNpc = Game.world().environment().getEntities();
        for (IEntity entity : collectionNpc) {
            if (entity.getName() != null) {
                if (entity.getName().contains("NPC-")) {
                    litiInteractables.add(new LitiInteractable(entity, true));
                } else if (entity.getName().contains("CHEST-")) {
                    litiInteractables.add(new LitiInteractable(entity, false));
                }
            }
        }
    }

    public static ArrayList<LitiInteractable> getInteractables() {
        return litiInteractables;
    }

    public static void loadFonts() throws IOException, FontFormatException {
        String pathName = "./Fonts";
        File path = new File(pathName);
        String[] fontFilesNames = path.list();
        for (String fontFileName : fontFilesNames) {
            File fontFile = new File(pathName + "/" + fontFileName);
            Font gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            gameFont = gameFont.deriveFont(10.f);
            gameFonts.add(gameFont);
        }
    }

    public static ArrayList<Font> getGameFonts() {
        return gameFonts;
    }
}





