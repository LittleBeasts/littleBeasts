package com.littlebeasts;

import com.littlebeasts.gameLogic.GameState;
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
        String input[] = new String[1];
        input[0] = "Arkham";
        if (!Game.hasStarted()) {
            Program.main(input);
        }
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.MENU);
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
        TestRobot.robotButtonPress(KeyEvent.VK_ESCAPE);
        Assert.assertEquals(Program.getGameLogic().getState(), GameState.INGAME);
    }
}
