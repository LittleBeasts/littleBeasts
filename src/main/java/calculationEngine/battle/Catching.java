package calculationEngine.battle;

import calculationEngine.entities.Attack;
import calculationEngine.entities.BeastTypes;
import calculationEngine.entities.CeEntity;
import calculationEngine.entities.CePlayer;
import calculationEngine.environment.Item;

import java.util.Random;

public class Catching {
    private static Random rnd = new Random();
    private static String debugInfo; //string to store debug information
    private static boolean bDebug = false; //show rolls in console

    public static boolean isCaught(CePlayer player, CeEntity beast, Item cage) {

        if (beast.getType() == BeastTypes.PlayerStandard && !beast.isWild()) return false;
        //roll to d50 to get a quasi normal distribution
        int attackRoll = rnd.nextInt(51) + rnd.nextInt(51);
        debugInfo = "Roll: " + attackRoll + "\n";
        Attack catchingSkill = player.getPlayerStandardAttacks()[0];

        if (catchingMisses(player.getCeEntity(), beast, catchingSkill, attackRoll, cage)) {
            debugInfo += "Miss\n";
            return false;
        }
        debugInfo += "Catch\n";

        if (bDebug) System.out.println(debugInfo);
        return true;
    }

    private static boolean catchingMisses(CeEntity player, CeEntity beast, Attack attack, int attackRoll, Item cage) {
        // when it is a critical hit, it will never miss
        if (attackRoll > (100 - attack.getCriticalChance())) {
            debugInfo += "Critical Catch\n";
            return false;
        }
        // base difficulty is percent of the HP of the beast, then add the speed of the defender. After that the speed of the attacker and the accuracy of the attack are subtracted.
        int baseDifficultly = (beast.getHitPoints() * 100) / (beast.getMaxHitPoints());
        double levelModifier = player.getLevel() / beast.getLevel();
        int speed = (int) (player.getSpeed() * levelModifier);
        int difficulty = baseDifficultly + beast.getSpeed() - (speed + attack.getAccuracy() + cage.getBonus());
        debugInfo += "Difficulty: " + difficulty + "\n";
        // if difficulty is higher than the roll the attack will miss.
        return difficulty > attackRoll;
    }

}
