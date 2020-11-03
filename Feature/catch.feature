Feature: Catch a littleBeast

  Scenario: Player picks an activity
    Given the Player is in a battle
    And the Player is not dead
    When the Player chooses catch
    Then a menu opens where the Player choose a catching option

  Scenario: Player picks a cage to catch the littleBeast
    Given the catch menu is open
    When the Player chooses a cage
    Then the Player tries to catch the littleBeast with the cage

  Scenario: Player hit littleBeast with the cage
    Given the Player chose a cage
    When the Player hits a littleBeast with a cage
    Then the Player has a chance to catch the littleBeast

  Scenario: Player misses the littleBeast
    Given the Player chose a cage
    When the Player misses the littleBeast
    Then the Player does not catch the littleBeast

  Scenario: Player catches littleBeast
    Given the Player hit the littleBeast with a cage
    When the Player rolls
    And difficulty is lower than the roll
    Then the Player catches the littleBeast

  Scenario: Player does not catch littleBeast
    Given the Player hit the littleBeast with a cage
    When the Player rolls
    And difficulty is higher than the roll
    Then the Player does not catch the littleBeast