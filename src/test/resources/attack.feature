@Attack @All
Feature: Attack a character

  @AttackPlayerPicksActivity @Done
  Scenario: Player picks an activity
    Given the Player is in a battle
    And the Player is not dead
    When the Player chooses to attack
    Then a menu opens where the Player can choose an attack

  Scenario: Player picks an attack
    Given the attack menu is open
    When the Player chooses an attack
    Then the Player uses the chosen attack

  Scenario: Player attacks and hits
    Given the Player chose an attack
    When the Player hits an enemy with an attack
    Then the Player deals damage to its target

  Scenario: Player attacks and misses
    Given the Player chose an attack
    When the Player misses the attack on the enemy
    Then the Player does not deal damage to its target