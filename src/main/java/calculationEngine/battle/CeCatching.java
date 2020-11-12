package calculationEngine.battle;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeBeastTypes;
import calculationEngine.entities.CeEntity;
import calculationEngine.entities.CePlayer;
import calculationEngine.environment.CeItem;
import config.GlobalConfig;

import java.util.Random;

public class CeCatching {
    private static Random random = new Random();
    private static String debugInfo;
    private final static boolean bDebug = GlobalConfig.DEBUG_CONSOLE_OUT;

    public static boolean isCaught(CePlayer player, CeEntity beast, CeItem cage) {

        if (beast.getCeStats().getType() == CeBeastTypes.PlayerStandard && !beast.isWild()) return false;
        //roll to d50 to get a quasi normal distribution
        int attackRoll = random.nextInt(51) + random.nextInt(51);
        debugInfo = "Roll: " + attackRoll + "\n";
        CeAttack catchingSkill = player.getPlayerStandardAttacks()[0];

        if (catchingMisses(player, beast, catchingSkill, attackRoll, cage)) {
            debugInfo += "Miss\n";
            return false;
        }
        debugInfo += "Catch\n";

        if (bDebug) System.out.println(debugInfo);
        return true;
    }

    private static boolean catchingMisses(CeEntity player, CeEntity beast, CeAttack ceAttack, int attackRoll, CeItem cage) {
        // when it is a critical hit, it will never miss
        if (attackRoll > (100 - ceAttack.getCriticalChance())) {
            debugInfo += "Critical Catch\n";
            return false;
        }
        // base difficulty is percent of the HP of the beast, then add the speed of the defender. After that the speed of the attacker and the accuracy of the attack are subtracted.
        int baseDifficultly = (beast.getCeStats().getCurrentHitPoints() * 100) / (beast.getCeStats().getMaxHitPoints());
        double levelModifier = (double) player.getCeStats().getLevel() / beast.getCeStats().getLevel();
        int speed = (int) (player.getCeStats().getSpeed() * levelModifier);
        int difficulty = baseDifficultly + beast.getCeStats().getSpeed() - (speed + ceAttack.getAccuracy() + cage.getBonus());
        debugInfo += "Difficulty: " + difficulty + "\n";
        // if difficulty is higher than the roll the attack will miss.
        return difficulty > attackRoll;
    }
}
