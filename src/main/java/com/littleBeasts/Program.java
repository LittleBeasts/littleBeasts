package com.littleBeasts;

import calculationEngine.battle.Damage;
import calculationEngine.entities.*;
import calculationEngine.environment.Loot;
import com.littleBeasts.screens.IngameScreen;
import com.littleBeasts.screens.MenuScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import org.json.JSONObject;

public class Program {

    private static GameLogic gameLogic;
    private static IngameScreen ingameScreen;
    private static MenuScreen menuScreen;

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


        // add the screens
        ingameScreen = new IngameScreen();
        Game.screens().add(ingameScreen);
        menuScreen = new MenuScreen();
        Game.screens().add(menuScreen);


        // initialize modules
        PlayerInput.init();
        gameLogic = new GameLogic();
        gameLogic.init();

        // enter main menu
        GameLogic.setState(GameState.MENU);
        Game.screens().display("MAINMENU");
        Game.audio().playMusic("titlemenu");
        //Game.audio().playMusic("mainMenu");
        // GameLogic.setState(GameState.INGAME);
        // Game.screens().display("INGAME-SCREEN");
        // Game.world().loadEnvironment("Arkham");

        //-----------------------------------------------------------------------------------------------------------
        // test "fight"
        Attack[] attacks = new Attack[1];
        attacks[0] = new Attack(Attacks.Punch);
        CeEntity attacker = new CeEntity(BeastTypes.Earth, Nature.ANGRY, attacks, 10, 1, 10, 10, 10, 10, 10, 4, 1, true);
        CeEntity defender = new CeEntity(BeastTypes.Water, Nature.ANGRY, attacks, 10, 1, 10, 10, 10, 10, 10, 4, 1, true);
        int damage = Damage.calculateDamage(attacker, defender, attacker.getAttacks()[0]);
        System.out.println("Damage: " + damage);
        //-------------------------------------------------------------------------------------------------------------

        //-------------------------------------------------------------------------------------------------------------
        // test "loot"
        JSONObject[] jsonObject = Loot.getLootBySource("monster");
        for (JSONObject jo : jsonObject) {
            System.out.println(jo);
        }
        //-------------------------------------------------------------------------------------------------------------
        Game.start();
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
