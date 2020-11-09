@StartGame @All @Done
Feature: Start the game

  @StartGamePlayerStartsGame @Done
  Scenario: Player starts the game
    Given the Player is in the main menu
    When the Player presses the button to start the game
    Then the game starts