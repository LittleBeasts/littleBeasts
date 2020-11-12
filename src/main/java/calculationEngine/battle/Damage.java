package calculationEngine.battle;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeEntity;
import config.GlobalConfig;

import java.util.Random;

public class Damage {
    private static Random random = new Random();
    private static String debugInfo;
    private final static boolean bDebug = GlobalConfig.DEBUG_CONSOLE_OUT;

    public static int calculateDamage(CeEntity attacker, CeEntity defender, CeAttack ceAttack) {
        //roll to d50 to get a quasi normal distribution
        int attackRoll = random.nextInt(51) + random.nextInt(51);
        debugInfo = "Roll: " + attackRoll + "\n";
        // if the attack misses -1 is returned
        if (attackMisses(attacker, defender, ceAttack, attackRoll)) {
            debugInfo += "Miss\n";
            return -1;
        }
        debugInfo += "Hit\n";
        // gets attack and defense power.
        int attackPower = calculateAttack(attacker, ceAttack, attackRoll);
        int defensePower = calculateDefense(defender, ceAttack);

        debugInfo += "Attack: " + attackPower + "\n";
        debugInfo += "Defense: " + defensePower + "\n";
        if (bDebug) System.out.println(debugInfo);
        // returns the damage, if the damage would be smaller than zero it will return zero.
        return Math.max((attackPower - defensePower), 0);
    }

    private static boolean attackMisses(CeEntity attacker, CeEntity defender, CeAttack ceAttack, int attackRoll) {
        // when it is a critical hit, it will never miss
        if (attackRoll > (100 - ceAttack.getCriticalChance())) {
            debugInfo += "Critical Hit\n";
            return false;
        }
        // base difficulty is 50 then add the speed of the defender. After that the speed of the attacker and the accuracy of the attack are subtracted.
        int difficulty = 50 + defender.getSpeed() - (attacker.getSpeed() + ceAttack.getAccuracy());
        debugInfo += "Difficulty: " + difficulty + "\n";
        // if difficulty is higher than the roll the attack will miss.
        return difficulty > attackRoll;
    }

    private static int calculateDefense(CeEntity defender, CeAttack ceAttack) {
        //the modifier is calculated in BeastTypes.
        return (int) (defender.getDefense() * defender.getType().getModifier(ceAttack.getType()));
    }

    private static int calculateAttack(CeEntity attacker, CeAttack ceAttack, int attackRoll) {
        //if it is a critical hit, damage will be doubled.
        int critModifier = attackRoll > (100 - ceAttack.getCriticalChance()) ? 2 : 1;
        // attack is calculated with rolls. Two rolls from the base stat of the attacker and two from the roll of the attack to get a pseudo normal distributed value.
        int damageRoll = random.nextInt(attacker.getAttack() + 1) + random.nextInt(attacker.getAttack() + 1) + random.nextInt(ceAttack.getDamage() + 1) + random.nextInt(ceAttack.getDamage() + 1);
        debugInfo += "Damage roll: " + damageRoll + "\n";

        return damageRoll * critModifier;
    }
}
