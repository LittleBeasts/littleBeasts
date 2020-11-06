package com.littleBeasts;

import calculationEngine.entities.Beasts;
import com.littleBeasts.entities.Beast;
import com.littleBeasts.entities.Player;
import com.littleBeasts.screens.IngameScreen;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.MapArea;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameLogic implements IUpdateable {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;

    public static final Font MENU_FONT = new Font("Serif", Font.BOLD, 13);
    public static String START_LEVEL = "Arkham";

    private static List<Beast> beastList;
    private Camera camera;

    public GameLogic() {

    }

    /**
     * Initializes the game logic for the game.
     */
    public void init() {
        Game.loop().attach(this);
        beastList = new ArrayList<>();
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
        GameLogic.state = state;
        Game.loop().setTimeScale(1);
        Player.instance().attachControllers();
        for (int i = 0; i < beastList.size(); i++) {
            beastList.get(i).removeFromMap();
        }
        switch (state) {
            case MENU:
                if (!firstStart) {
                    Game.loop().setTimeScale(0);
                    Game.screens().display("MAINMENU");
                    Game.audio().playMusic("titlemenu");
                }
                break;
            case BATTLE:
                Player.instance().detachControllers();
                Game.audio().playMusic("battle");
                beastList.add(new Beast(Beasts.FeuerFurz, (int) Player.instance().getX() + 50,
                        (int) (Player.instance().getY() - (Player.instance().getHeight() / 2)),
                        Player.instance().getFacingDirection().getOpposite())); //for dev purposes
                break;
            case INGAME:
                firstStart = false;
                IngameScreen.chatWindow.setVisible(false);
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
                Game.loop().setTimeScale(0);
                Player.instance().detachControllers();
                IngameScreen.chatWindow.setVisible(true);
                IngameScreen.chatWindow.setFocus(true);
                Input.keyboard().onKeyTyped(e -> {
                    IngameScreen.chatWindow.add(e);
                });
                Game.audio().playMusic("ingameMenu");
                break;
        }
        System.out.println(GameLogic.state.name());
    }

    @Override
    public void update() {
        loadNewArea();
    }

    public void loadNewArea(){
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
}





