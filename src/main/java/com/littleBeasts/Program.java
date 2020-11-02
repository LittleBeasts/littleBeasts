package com.littleBeasts;

import com.littleBeasts.screens.IngameScreen;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.environment.tilemap.IMap;
import de.gurkenlabs.litiengine.resources.Resources;

public class Program {

    public static void main(String[] args) {

        //set game meta information
        Game.info().setName("littleBeasts");
        Game.info().setSubTitle("");
        Game.info().setVersion("v.0");

        // initialize the game infrastructure
        Game.init(args);

        //set icon for the game
        Game.window().setIcon(Resources.images().get("sprites/icon.png"));
        Game.graphics().setBaseRenderScale(4.001f);

        // Load data from the utiLiti game file
        Resources.load("game.litidata");

        PlayerInput.init();
        GameLogic.init();

        // add the screens
        Game.screens().add(new IngameScreen());

        Game.world().loadEnvironment("Arkham");
        Game.start();
    }
}
