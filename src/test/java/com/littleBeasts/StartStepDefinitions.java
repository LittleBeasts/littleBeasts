package com.littleBeasts;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.awt.event.KeyEvent;
import java.io.IOException;

public class StartStepDefinitions {
    @Given("^the Player is in the main menu$")
    public void thePlayerIsInTheMainMenu() throws IOException {
        Program.main(new String[42]);
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.MENU);
        System.out.println("Möööp");
    }

    @When("^the Player presses the button to start the game$")
    public void thePlayerPressesTheButtonToStartTheGame() {
        Program.getGameLogic().robotButtonPress(KeyEvent.VK_UP);
        Program.getGameLogic().robotButtonPress(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Then("^the game starts$")
    public void theGameStarts() {
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.INGAME);
    }
}