package com.littleBeasts;

import com.littleBeasts.entities.Player;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

public class StepDefinitions {


    @Given("the Player is in a battle")
    public void the_Player_is_in_a_battle() {
        System.out.println("In Battle *Epic music starts playing*");
    }

    @Given("the Player is not dead")
    public void the_Player_is_not_dead() {
        System.out.println(Player.instance().isDead());
    }

    @When("the Player chooses to attack")
    public void the_Player_chooses_to_attack() {
        System.out.println("Pikachu uses Thunderbolt");
    }

    @When("a menu opens where the Player can choose an attack")
    public void a_men_opens_where_the_Player_can_choose_an_attack() {
        System.out.println("Pikachu killed the Pokemon of the little girl");
    }


}
