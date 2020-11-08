Feature: Start the game

  Scenario: Player starts the game
    Given the Player is in the main menu
    When the Player presses the button to start the game
    Then the game starts