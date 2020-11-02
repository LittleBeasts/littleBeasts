package com.littleBeasts;

import calculationEngine.Loot;
import calculationEngine.entities.*;
import com.littleBeasts.screens.MenuScreen;
import com.littleBeasts.screens.IngameScreen;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import org.json.JSONObject;

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


        // add the screens
        Game.screens().add(new IngameScreen());
        Game.screens().add(new MenuScreen());


        // initialize modules
        PlayerInput.init();
        GameLogic.init();


        // enter main menu
        GameLogic.setState(GameState.MENU);
        Game.screens().display("MAINMENU");
        // GameLogic.setState(GameState.INGAME);
        // Game.screens().display("INGAME-SCREEN");
        // Game.world().loadEnvironment("Arkham");


        //-----------------------------------------------------------------------------------------------------------
        // test "fight"
        Attack[] attacks = new Attack[1];
        attacks[0] = new Attack(Attacks.Punch);
        CeEntity attacker = new CeEntity(BeastTypes.Earth, Nature.ANGRY, attacks, 10, 1, 10, 10, 10, 10, 10, 4, 1);
        CeEntity defender = new CeEntity(BeastTypes.Water, Nature.ANGRY, attacks, 10, 1, 10, 10, 10, 10, 10, 4, 1);
        int damage = Damage.calculateDamage(attacker, defender, attacker.getAttacks()[0]);
        System.out.println("Damage: " + damage);
        //-------------------------------------------------------------------------------------------------------------





        JSONObject[] jsonObject = Loot.getLootBySource("monster");
        for (JSONObject jo : jsonObject) {
            System.out.println(jo);
        }
        Game.start();
    }
}
