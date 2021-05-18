package com.littleBeasts;

import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.MapNames;
import com.littleBeasts.screens.IngameScreen;
import com.littleBeasts.screens.MenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import utilities.LitiFonts;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Program {

    private static GameLogic gameLogic;
    private static IngameScreen ingameScreen;
    private static String startingMap = null;

    public static void main(String[] args) throws IOException, FontFormatException {
        LitiFonts.loadFonts();
        //set game meta information
        Game.info().setName("littleBeasts");
        Game.info().setSubTitle("");
        Game.info().setVersion("v.0");
        if (args.length > 0)
            startingMap = args[0];

        startingMap = MapNames.Arkham.toString();
        // initialize the game infrastructure
        Game.init();

        //set icon for the game

        Game.window().setIcon(ImageIO.read(Objects.requireNonNull(Program.class.getResourceAsStream("/sprites/icon.png"))));
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
