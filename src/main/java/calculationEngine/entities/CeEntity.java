package calculationEngine.entities;

import config.EntityConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CeEntity {
// |--------------------------------------------------------------------------|
// |    This class is the parent class for every littleBeast.                 |
// |    Every Beast will inherit the props and funcs of this class and        |
// |    extend them.                                                          |
// |--------------------------------------------------------------------------|

    // properties
    private BeastTypes type;
    private Nature nature;
    private Attack[] attacks;

    // development logic isn't implemented yet -> has to be decided if we want to use this.
    private Beasts development; // Development beast
    private int developmentLvl; // level at which development will take place

    // stats
    private int maxHitPoints; //TODO: added max hit points, calculations for it need to be addressed.
    private int hitPoints;
    private int level;
    private int friendshipPoints;
    private int speed;
    private int stamina;
    private int attack;
    private int defense;
    private boolean wild;
    private Random random = new Random();

    public CeEntity(BeastTypes type, Nature nature, Attack[] attacks, int hitPoints, int maxHitPoints, int level, int friendshipPoints, int speed, int stamina, int attack, int defense, int developmentLvl, boolean wild) {
        this.type = type;
        this.nature = nature;
        this.attacks = attacks;
        this.hitPoints = hitPoints;
        this.maxHitPoints = maxHitPoints;
        this.level = level;
        this.friendshipPoints = friendshipPoints;
        this.speed = speed;
        this.stamina = stamina;
        this.attack = attack;
        this.defense = defense;
        this.developmentLvl = developmentLvl;
        this.wild = false;
    }

    public CeEntity(Regions region, CeEntity player) { // Constructor for new Encounter Beast

        Beasts beast = pickBeast(region);

        // calculate Level
        int tmpLvl = calcLvl(player);
        boolean lvlInRange = EntityConstants.MAX_LVL >= tmpLvl && EntityConstants.START_LVL <= tmpLvl;
        this.level = lvlInRange ? tmpLvl : (tmpLvl <= EntityConstants.MAX_LVL ? EntityConstants.START_LVL : EntityConstants.MAX_LVL); // Sets Level to constant if level isn't in specified range

        Nature[] natures = Nature.values();
        this.nature = natures[random.nextInt(natures.length)];

        this.developmentLvl = beast.getDevelopmentlvl();
        this.hitPoints = scaleOnLvl(beast.getBaseHp(), beast.getHpLvlScaling());
        this.type = beast.getType();
        this.speed = scaleOnLvl(beast.getBaseSpeed(), beast.getSpeedLvlScaling());
        this.attack = scaleOnLvl(beast.getBaseAttack(), beast.getAttackLvlScaling()) + (random.nextInt(EntityConstants.ATTACK_RANGE * 2) - EntityConstants.ATTACK_RANGE);
        this.stamina = scaleOnLvl(beast.getBaseAttack(), beast.getStaminaLvlScaling());
        this.defense = scaleOnLvl(beast.getBaseDefense(), beast.getDefenseLvlScaling()) + (random.nextInt(EntityConstants.DEFENSE_RANGE * 2) - EntityConstants.DEFENSE_RANGE);
        this.attacks = pickAttacks();
        this.wild = true;
    }


    public CeEntity(Beasts beast) { // dev constructor
        this.type = beast.getType();
        Nature[] natures = Nature.values();
        this.nature = natures[random.nextInt(natures.length)];
        //this.attacks = beast.getAttacks;
        this.hitPoints = beast.getBaseAttack();
        this.maxHitPoints = beast.getBaseHp();
        this.level = 1;
        this.friendshipPoints = 0;
        this.speed = beast.getBaseSpeed();
        this.stamina = beast.getBaseStamina();
        this.attack = beast.getBaseAttack();
        this.defense = beast.getBaseDefense();
        this.developmentLvl = beast.getDevelopmentlvl();
        this.wild = true;
    }


    private int scaleOnLvl(int base, int lvlScaling) {
        return base + this.getLevel() * lvlScaling;
    }

    private int calcLvl(CeEntity player) {
        Random random = new Random();
        return player.getLevel() + (random.nextInt(EntityConstants.LVL_RANGE * 2) - EntityConstants.LVL_RANGE);

    }

    private Beasts pickBeast(Regions region) {
        Beasts[] allBeasts = Beasts.values();
        List<Beasts> availableBeasts = new ArrayList<>();
        Random random = new Random();
        for (Beasts beast : allBeasts) {
            if (beast.getRegion() == region) availableBeasts.add(beast);
        }
        return availableBeasts.get(random.nextInt(availableBeasts.size()));
    }

    private Attack[] pickAttacks() {
        Random random = new Random();
        Attack[] pickedAttacks = new Attack[4];
        List<Attacks> typedAttacks = new ArrayList<>();
        for (Attacks attack : Attacks.values()) {
            if (attack.getType() == type) {
                typedAttacks.add(attack);
            }
        }
        for (int i = 0; i < pickedAttacks.length; i++) {
            int index = random.nextInt(typedAttacks.size());
            pickedAttacks[i] = new Attack(typedAttacks.remove(index));
        }

        return pickedAttacks;
    }

    public int getDevelopmentLvl() {
        return developmentLvl;
    }

    public BeastTypes getType() {
        return type;
    }

    public Nature getNature() {
        return nature;
    }

    public Beasts getDevelopment() {
        return development;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public int getLevel() {
        return level;
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
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

    public boolean isWild() {
        return wild;
    }

    public int getDefense() {
        return defense;
    }

    public void setType(BeastTypes type) {
        this.type = type;
    }

    public void setDevelopment(Beasts development) {
        this.development = development;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }
}
