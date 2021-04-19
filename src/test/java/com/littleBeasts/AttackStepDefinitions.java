package com.littleBeasts;


import com.littleBeasts.entities.LitiPlayer;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import java.awt.event.KeyEvent;


import static com.littleBeasts.GameLogic.robotButtonPress;


public class AttackStepDefinitions {


    //Scenario 1
    @Given("^the Player is in a battle$")
    public void thePlayerIsInABattle() {
        Program2.main(new String[]{});
        if (GameLogic.getState() == GameState.MENU){
            robotButtonPress(KeyEvent.VK_UP);
            robotButtonPress(KeyEvent.VK_ENTER);
        }
        Assert.assertEquals(GameLogic.getState(),GameState.INGAME);
        robotButtonPress(KeyEvent.VK_B);
        Assert.assertEquals(GameLogic.getState(),GameState.BATTLE);

    }

    @And("^the Player is not dead$")
    public void thePlayerIsNotDead() {
        Assert.assertTrue(LitiPlayer.instance().getCePlayer().getCeStats().getCurrentHitPoints() > 0);
    }

    @When("^the Player chooses to attack$")
    public void thePlayerChoosesToAttack() {
        Assert.assertTrue(Program2.getIngameScreen().getHud().getBattleMenu().isFocused());
        robotButtonPress(KeyEvent.VK_D);
        Assert.assertFalse(Program2.getIngameScreen().getHud().getBattleMenu().isFocused());
    }

    @Then("^a menu opens where the Player can choose an attack$")
    public void aMenuOpensWhereThePlayerCanChooseAnAttack() {
        Assert.assertTrue(Program2.getIngameScreen().getHud().getAttackMenu().isFocused());
        robotButtonPress(KeyEvent.VK_E);
        robotButtonPress(KeyEvent.VK_B);
        Assert.assertEquals(GameLogic.getState(), GameState.INGAME);
        returnToMainMenu();
        Assert.assertEquals(GameLogic.getState(), GameState.MENU);
    }

    private void returnToMainMenu() {
        robotButtonPress(KeyEvent.VK_ESCAPE);
        robotButtonPress(KeyEvent.VK_DOWN);
        robotButtonPress(KeyEvent.VK_ENTER);
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

