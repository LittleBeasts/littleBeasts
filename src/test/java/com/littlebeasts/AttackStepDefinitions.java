package com.littlebeasts;


import com.littlebeasts.entities.LitiPlayer;
import com.littlebeasts.gamelogic.GameState;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class AttackStepDefinitions {


    //Scenario 1
    @Given("^the Player is in a battle$")
    public void thePlayerIsInABattle() throws IOException, FontFormatException {
        String input[] = new String[1];
        input[0] = "Arkham";
        Program.main(input);
        if (Program.getGameLogic().getState() == GameState.MENU) {
            TestRobot.robotButtonPress(KeyEvent.VK_UP);
            TestRobot.robotButtonPress(KeyEvent.VK_ENTER);
        }
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.INGAME);
        TestRobot.robotButtonPress(KeyEvent.VK_B);
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.BATTLE);
    }

    @And("^the Player is not dead$")
    public void thePlayerIsNotDead() {
        Assert.assertTrue(LitiPlayer.instance().getCePlayer().getCeStats().getCurrentHitPoints() > 0);
    }

    @When("^the Player chooses to attack$")
    public void thePlayerChoosesToAttack() {
        Assert.assertTrue(Program.getIngameScreen().getHud().getBattleMenu().isFocused());
        TestRobot.robotButtonPress(KeyEvent.VK_D);
        Assert.assertFalse(Program.getIngameScreen().getHud().getBattleMenu().isFocused());
    }

    @Then("^a menu opens where the Player can choose an attack$")
    public void aMenuOpensWhereThePlayerCanChooseAnAttack() {
        Assert.assertTrue(Program.getIngameScreen().getHud().getBattleMenu().getAttackMenu().isFocused());
        TestRobot.robotButtonPress(KeyEvent.VK_E);
        TestRobot.robotButtonPress(KeyEvent.VK_B);
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.INGAME);
        returnToMainMenu();
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.MENU);
    }

    private void returnToMainMenu() {
        TestRobot.robotButtonPress(KeyEvent.VK_ESCAPE);
        TestRobot.robotButtonPress(KeyEvent.VK_DOWN);
        TestRobot.robotButtonPress(KeyEvent.VK_ENTER);
    }


    //Scenario 2
    @Given("^the attack menu is open$")
    public void theAttackMenuIsOpen() {

    }

    @When("^the Player chooses an attack$")
    public void thePlayerChoosesAnAttack() {

    }

    @Then("^the Player uses the chosen attack$")
    public void thePlayerUsesTheChosenAttack() {
    }

    //Scenario 3
    @Given("^the Player chose an attack$")
    public void thePlayerChoseAnAttack() {
    }

    @When("^the Player hits an enemy with an attack$")
    public void thePlayerHitsAnEnemyWithAnAttack() {
    }

    @Then("^the Player deals damage to its target$")
    public void thePlayerDealsDamageToItsTarget() {
    }

    //Scenario 4
    @When("^the Player misses the attack on the enemy$")
    public void thePlayerMissesTheAttackOnTheEnemy() {
    }

    @Then("^the Player does not deal damage to its target$")
    public void thePlayerDoesNotDealDamageToItsTarget() {
    }

}
