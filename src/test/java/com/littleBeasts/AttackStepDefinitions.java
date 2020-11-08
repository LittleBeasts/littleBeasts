package com.littleBeasts;

import com.littleBeasts.entities.Player;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.event.KeyEvent;


public class AttackStepDefinitions {


    //Scenario 1
    @Given("^the Player is in a battle$")
    public void thePlayerIsInABattle() {

        Program.main(new String[42]);
        Program.getGameLogic().buttonPressed(KeyEvent.VK_UP);
        Program.getGameLogic().buttonPressed(KeyEvent.VK_ENTER);

        Assert.assertEquals(Program.getGameLogic().getState(),GameState.INGAME);
        Program.getGameLogic().buttonPressed(KeyEvent.VK_B);
        Assert.assertEquals(Program.getGameLogic().getState(),GameState.BATTLE);

    }

    @And("^the Player is not dead$")
    public void thePlayerIsNotDead() {
        Assert.assertTrue(Player.instance().getCePlayer().getCeEntity().getHitPoints() > 0);
    }

    @When("^the Player chooses to attack$")
    public void thePlayerChoosesToAttack() {
        Assert.assertTrue(Program.getIngameScreen().getHud().getBm().isFocused());
        Program.getGameLogic().buttonPressed(KeyEvent.VK_E);
        Assert.assertFalse(Program.getIngameScreen().getHud().getBm().isFocused());
    }

    @Then("^a menu opens where the Player can choose an attack$")
    public void aMenuOpensWhereThePlayerCanChooseAnAttack() {
        Assert.assertTrue(Program.getIngameScreen().getHud().getAttackMenu().isFocused());
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

