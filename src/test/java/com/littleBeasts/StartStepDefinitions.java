package com.littleBeasts;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.gurkenlabs.litiengine.Game;
import org.junit.Assert;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class StartStepDefinitions {
    @Given("^the Player is in the main menu$")
    public void thePlayerIsInTheMainMenu() throws IOException, FontFormatException {
        if(!Game.hasStarted()) {
            Program.main(new String[]{});
        }
        Assert.assertEquals(GameLogic.getState(), GameState.MENU);
        System.out.println("Möööp");
    }

    @When("^the Player presses the button to start the game$")
    public void thePlayerPressesTheButtonToStartTheGame() {
        TestRobot.robotButtonPress(KeyEvent.VK_UP);
        TestRobot.robotButtonPress(KeyEvent.VK_ENTER);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Then("^the game starts$")
    public void theGameStarts() {
//        GameLogic.robotButtonPress(KeyEvent.VK_ESCAPE);
        Assert.assertEquals(GameLogic.getState(), GameState.INGAME);
    }
}
