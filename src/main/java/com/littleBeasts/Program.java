package com.littleBeasts;

import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.MapNames;
import com.littleBeasts.screens.IngameScreen;
import com.littleBeasts.screens.MenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Program {

    private static GameLogic gameLogic;
    private static IngameScreen ingameScreen;
    private static String startingMap = null;
    public static HashMap<String, Spritesheet> spritesheetMap;

    public static void main(String[] args) throws IOException, FontFormatException {
        //set game meta information
        Game.info().setName("littleBeasts");
        Game.info().setSubTitle("");
        Game.info().setVersion("v.0");
        if (args.length > 0)
            startingMap = args[0];

        startingMap = MapNames.residentialArea.toString();
        // initialize the game infrastructure
        Game.init();

        //set icon for the game
        Game.window().setIcon(Resources.images().get(Objects.requireNonNull(Program.class.getResource("/sprites/icon.png")).getPath()));
        Game.graphics().setBaseRenderScale(1.001f);

        // Load data from the utiLiti game file
        Resources.load(Program.class.getResource("/game.litidata"));

        // add the screens
        ingameScreen = new IngameScreen();
        Game.screens().add(ingameScreen);
        MenuScreen menuScreen = new MenuScreen();
        Game.screens().add(menuScreen);

        // initialize modules
        PlayerInput.init();
        gameLogic = new GameLogic();
        gameLogic.init();

        // enter main menu
        gameLogic.setState(GameState.MENU);
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
