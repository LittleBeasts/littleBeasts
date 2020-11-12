package calculationEngine.entities;

import config.AiConstants;
import config.EntityConstants;
import config.PlayerConfig;

import java.util.Random;

public class CeStats {

    private final CeBeastTypes type;
    private final CeNature nature;
    private int level;
    private int currentHitPoints;
    private int maxHitPoints;
    private int speed;
    private int stamina;
    private int attack;
    private int defense;
    private int friendshipPoints;

    private Random random = new Random();

    // Constructor for Save games
    public CeStats(CeBeastTypes type, CeNature nature, int level, int currentHitPoints, int maxHitPoints, int speed, int stamina, int attack, int defense, int friendshipPoints) {
        this.level = level;
        this.currentHitPoints = currentHitPoints;
        this.maxHitPoints = maxHitPoints;
        this.speed = speed;
        this.stamina = stamina;
        this.attack = attack;
        this.defense = defense;
        this.friendshipPoints = friendshipPoints;
        this.type = type;
        this.nature = nature;
    }

    // Constructor for a new random Beast
    public CeStats(CeBeasts beast, int playerLvl){
        // calculate Level
        this.level = calcLvl(playerLvl);
        this.nature = CeNature.getRandomNature();

        this.currentHitPoints = scaleOnLvl(beast.getBaseHp(), this.level, beast.getHpLvlScaling());
        this.maxHitPoints = this.currentHitPoints;
        this.type = beast.getType();
        this.speed = scaleOnLvl(beast.getBaseSpeed(), this.level, beast.getSpeedLvlScaling());
        this.attack = scaleOnLvl(beast.getBaseAttack(), this.level, beast.getAttackLvlScaling()) + (random.nextInt(EntityConstants.ATTACK_RANGE * 2) - EntityConstants.ATTACK_RANGE);
        this.stamina = scaleOnLvl(beast.getBaseAttack(), this.level, beast.getStaminaLvlScaling());
        this.defense = scaleOnLvl(beast.getBaseDefense(), this.level, beast.getDefenseLvlScaling()) + (random.nextInt(EntityConstants.DEFENSE_RANGE * 2) - EntityConstants.DEFENSE_RANGE);
    }

    public CeStats(CeBeasts beast){ // dev constructor
        this.type = beast.getType();
        this.nature = CeNature.getRandomNature();
        this.currentHitPoints = beast.getBaseHp();
        this.maxHitPoints = beast.getBaseHp();
        this.level = 1;
        this.friendshipPoints = 0;
        this.speed = beast.getBaseSpeed();
        this.stamina = beast.getBaseStamina();
        this.attack = beast.getBaseAttack();
        this.defense = beast.getBaseDefense();
    }

    // AI NPC Constructor
    public CeStats(int playerLvl, CeBeastTypes type){
        this.level = calcLvl(playerLvl);
        this.type = type;
        this.nature = CeNature.getRandomNature();

        this.currentHitPoints = scaleOnLvl(AiConstants.AI_BASE_HP, playerLvl, AiConstants.AI_LEVEL_SCALING);
        this.maxHitPoints = this.currentHitPoints;
        this.speed = scaleOnLvl(AiConstants.AI_BASE_SPEED, playerLvl, AiConstants.AI_LEVEL_SCALING);
        this.stamina = scaleOnLvl(AiConstants.AI_BASE_STAMINA, playerLvl, AiConstants.AI_LEVEL_SCALING);
        this.attack = scaleOnLvl(AiConstants.AI_BASE_ATTACK, playerLvl, AiConstants.AI_LEVEL_SCALING);
        this.defense = scaleOnLvl(AiConstants.AI_BASE_DEFENSE, playerLvl, AiConstants.AI_LEVEL_SCALING);
        this.friendshipPoints = 0;
    }

    private int calcLvl(int playerLvl) {
        int tmpLevel = playerLvl + (random.nextInt(EntityConstants.LVL_RANGE * 2) - EntityConstants.LVL_RANGE);
        boolean lvlInRange = EntityConstants.MAX_LVL >= tmpLevel && EntityConstants.START_LVL <= tmpLevel;
        return lvlInRange ? tmpLevel : (tmpLevel <= EntityConstants.MAX_LVL ? EntityConstants.START_LVL : EntityConstants.MAX_LVL); // Sets Level to constant if level isn't in specified range

    }

    public static int scaleOnLvl(int base, int lvl, int lvlScaling) {
        return base + lvl * lvlScaling;
    }

    public CeBeastTypes getType() {
        return type;
    }

    public CeNature getNature() {
        return nature;
    }

    public int getLevel() {
        return level;
    }

    public int getCurrentHitPoints() {
        return currentHitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStamina() {
        return stamina;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCurrentHitPoints(int currentHitPoints) {
        this.currentHitPoints = currentHitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }
}
