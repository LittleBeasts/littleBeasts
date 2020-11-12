package com.littleBeasts;

import com.littleBeasts.entities.Player;
import com.littleBeasts.screens.IngameScreen;
import com.littleBeasts.screens.MenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;

import java.io.IOException;

public class Program {

    private static GameLogic gameLogic;
    private static IngameScreen ingameScreen;
    private static MenuScreen menuScreen;

    // ToDo: Make Class Static again
    Program() throws IOException {
        runGame();
    }

    // ToDo: Remove unnecessary Constructor
    private void runGame() throws IOException {
        //set game meta information
        Game.info().setName("littleBeasts");
        Game.info().setSubTitle("");
        Game.info().setVersion("v.0");

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
        menuScreen = new MenuScreen();
        Game.screens().add(menuScreen);


        // initialize modules
        PlayerInput.init();
        gameLogic = new GameLogic();
        Player.instance().setGameLogic(gameLogic);
        gameLogic.init();

        // enter main menu
        GameLogic.setState(GameState.MENU);
        Game.screens().display("MAINMENU");
        Game.audio().playMusic("titlemenu");

//        //-------------------------------------------------------------------------------------------------------------
//        // test "loot"
//        JSONObject[] jsonObject = Loot.getLootBySource("monster");
//        for (JSONObject jo : jsonObject) {
//            System.out.println(jo);
//        }
//        //-------------------------------------------------------------------------------------------------------------
        Game.start();
    }

    public static void main(String[] args) throws IOException {
        Program program = new Program();
    }

    public static GameLogic getGameLogic() {
        return gameLogic;
    }

    public static IngameScreen getIngameScreen() {
        return ingameScreen;
    }

    public static MenuScreen getMenuScreen() {
        return menuScreen;
    }
}
