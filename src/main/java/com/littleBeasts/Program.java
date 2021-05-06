package com.littleBeasts;

import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.screens.IngameScreen;
import com.littleBeasts.screens.MenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.io.IOException;

public class Program {

    private static GameLogic gameLogic;
    private static IngameScreen ingameScreen;
    private static String startingMap = null;

    public static void main(String[] args) throws IOException, FontFormatException {
        //set game meta information
        Game.info().setName("littleBeasts");
        Game.info().setSubTitle("");
        Game.info().setVersion("v.0");
        if (args.length >0)
            startingMap = args[0];

        // initialize the game infrastructure
        Game.init();

        //set icon for the game
        Game.window().setIcon(Resources.images().get("sprites/icon.png"));
        Game.graphics().setBaseRenderScale(4.001f);

        // Load data from the utiLiti game file
        Resources.load("game.litidata");

        // add the screens
        ingameScreen = new IngameScreen();
        Game.screens().add(ingameScreen);
        MenuScreen menuScreen = new MenuScreen();
        Game.screens().add(menuScreen);

        // initialize modules
        PlayerInput.init();
        gameLogic = new GameLogic();
        LitiPlayer.instance().setGameLogic(gameLogic);
        gameLogic.init();

        // enter main menu
        GameLogic.setState(GameState.MENU);
        Game.screens().display("MAINMENU");
        Game.audio().playMusic("titlemenu");
        Game.start();
    }
    
    public static GameLogic getGameLogic() {
        return gameLogic;
    }

    public static IngameScreen getIngameScreen() {
        return ingameScreen;
    }

    public static String getStartingMap() {
        return startingMap;
    }
}
