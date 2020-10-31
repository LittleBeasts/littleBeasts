package calculationEngine.entities;

import java.util.Random;

public class Damage {
    private static Random rnd = new Random();
    private static String str; //string to store debug information
    private static boolean debug = false; //show damage roll in console

    public static int calculateDamage(CeEntity attacker, CeEntity defender, Attack attack) {
        //roll to d50 to get a quasi normal distribution
        int attackRoll = rnd.nextInt(51) + rnd.nextInt(51);
        str = "Roll: " + attackRoll + "\n";
        // if the attack misses -1 is returned
        if (attackMisses(attacker, defender, attack, attackRoll)) {
            str += "Miss\n";
            return -1;
        }
        str += "Hit\n";
        // gets attack and defense power.
        int attackPower = calculateAttack(attacker, attack, attackRoll);
        int defensePower = calculateDefense(defender, attack);

        str += "Attack: " + attackPower + "\n";
        str += "Defense: " + defensePower + "\n";
        if (debug) System.out.println(str);
        // returns the damage, if the damage would be smaller than zero it will return zero.
        return Math.max((attackPower - defensePower), 0);
    }

    private static boolean attackMisses(CeEntity attacker, CeEntity defender, Attack attack, int attackRoll) {
        // when it is a critical hit, it will never miss
        if (attackRoll > (100 - attack.getCriticalChance())) {
            str += "Critical Hit\n";
            return false;
        }
        // base difficulty is 50 then add the speed of the defender. After that the speed of the attacker and the accuracy of the attack are subtracted.
        int difficulty = 50 + defender.getSpeed() - (attacker.getSpeed() + attack.getAccuracy());
        str += "Difficulty: " + difficulty + "\n";
        // if difficulty is higher than the roll the attack will miss.
        return difficulty > attackRoll;
    }

    private static int calculateDefense(CeEntity defender, Attack attack) {
        //the modifier is calculated in BeastTypes.
        return (int) (defender.getDefense() * defender.getType().getModifier(attack.getType()));
    }

    private static int calculateAttack(CeEntity attacker, Attack attack, int attackRoll) {
        //if it is a critical hit, damage will be doubled.
        int critModifier = attackRoll > (100 - attack.getCriticalChance()) ? 2 : 1;
        // attack is calculated with rolls. Two rolls from the base stat of the attacker and two from the roll of the attack to get a pseudo normal distributed value.
        int damageRoll = rnd.nextInt(attacker.getAttack() + 1) + rnd.nextInt(attacker.getAttack() + 1) + rnd.nextInt(attack.getDamage() + 1) + rnd.nextInt(attack.getDamage() + 1);
        str += "Damage roll: " + damageRoll + "\n";

        return damageRoll * critModifier;
    }
}
