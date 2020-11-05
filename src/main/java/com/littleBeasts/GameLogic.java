package com.littleBeasts;

import com.littleBeasts.entities.Player;
import com.littleBeasts.screens.IngameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;
import de.gurkenlabs.litiengine.input.IKeyboard;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameLogic {
    private static GameState state = GameState.INGAME;
    private static boolean firstStart = true;

    public static final Font MENU_FONT = new Font("Serif", Font.BOLD, 13);
    public static String START_LEVEL = "Arkham";

    private GameLogic() {
    }

    /**
     * Initializes the game logic for the game.
     */
    public static void init() {

        //  Environment.registerMapObjectLoader(new CustomMapObjectLoader());

        // we'll use a camera in our game that is locked to the location of the player
        Camera camera = new PositionLockCamera(Player.instance());
        camera.setClampToMap(true);
        camera.setZoom(1.0f, 0);
        Game.world().setCamera(camera);

        // set a basic gravity for all levels.
        //Game.world().setGravity(120);

        // add default game logic for when a level was loaded
        // Game.world().onLoaded(e -> {
        //     // spawn the player instance on the spawn point with the name "enter"
        //     Spawnpoint enter = e.getSpawnpoint("west");
        //     if (enter != null) {
        //         enter.spawn(Player.instance());
        //     }
        // });

        Game.world().onLoaded(e -> {
            if (e.getMap().getName().equals("title")) {
                return;
            }

            // for (Prop prop : Game.world().environment().getProps()) {
            //     prop.setIndestructible(true);
            // }

            Game.loop().perform(500, () -> Game.window().getRenderComponent().fadeIn(500));

            // if (startups.containsKey(e.getMap().getName())) {
            //     startups.get(e.getMap().getName()).run();
            // }
//
            // if (e.getMap().getName().equals("end")) {
            //     return;
            // }

            // Player.instance().getHitPoints().setToMaxValue();
            Player.instance().setIndestructible(false);
            Player.instance().setCollision(true);
            // spawn the player instance on the spawn point with the name "enter"
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
        GameLogic.state = state;
        Game.loop().setTimeScale(1);
        Input.keyboard().removeKeyListener(chatKeyboard);
        if (getState() == GameState.INGAME) {
            firstStart = false;
            IngameScreen.chatWindow.setVisible(false);
            IngameScreen.ingameMenu.setVisible(false);
            //    Player.instance().addController();
        }
        if (getState() == GameState.INGAME_MENU) {
            Game.loop().setTimeScale(0);
            IngameScreen.ingameMenu.setVisible(true);
        }
        if (getState() == GameState.INGAME_CHAT) {
            Game.loop().setTimeScale(0);
            IngameScreen.chatWindow.setVisible(true);
            IngameScreen.chatWindow.setFocus(true);
            //  Player.instance().removeController();
            Input.keyboard().addKeyListener(chatKeyboard);
        }
        if (getState() == GameState.MENU && !firstStart) {
            Game.loop().setTimeScale(0);
            Game.screens().display("MAINMENU");
        }
        if (getState() == GameState.BATTLE) {
            Game.loop().setTimeScale(0);
        }

        System.out.println(GameLogic.state.name());
    }

    private static KeyListener chatKeyboard = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            IngameScreen.chatWindow.add(e.getKeyChar());
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // System.out.println("test");
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // System.out.println("test");
        }
    };

}
