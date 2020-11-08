package calculationEngine.entities;

import calculationEngine.environment.Regions;
import config.EntityConstants;

import java.util.ArrayList;
import java.util.Arrays;
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
    private CeAttack[] ceAttacks;

    // development logic isn't implemented yet -> has to be decided if we want to use this.
    private Beasts development; // Development beast
    private int developmentLvl; // level at which development will take place

    // stats
    private int maxHitPoints;
    private int hitPoints;
    private int level;
    private int friendshipPoints;
    private int speed;
    private int stamina;
    private int attack;
    private int defense;
    private boolean wild;
    private Random random = new Random();
    private List<Integer> damages = new ArrayList<>();

    private int playerNumber;

    public CeEntity(BeastTypes type, Nature nature, CeAttack[] ceAttacks, int hitPoints, int maxHitPoints, int level, int friendshipPoints, int speed, int stamina, int attack, int defense, int developmentLvl, boolean wild) {
        this.type = type;
        this.nature = nature;
        this.ceAttacks = ceAttacks;
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

        this.nature = Nature.getRandomNature();

        this.developmentLvl = beast.getDevelopmentlvl();
        this.hitPoints = scaleOnLvl(beast.getBaseHp(), this.level, beast.getHpLvlScaling());
        this.maxHitPoints = this.hitPoints;
        this.type = beast.getType();
        this.speed = scaleOnLvl(beast.getBaseSpeed(), this.level, beast.getSpeedLvlScaling());
        this.attack = scaleOnLvl(beast.getBaseAttack(), this.level, beast.getAttackLvlScaling()) + (random.nextInt(EntityConstants.ATTACK_RANGE * 2) - EntityConstants.ATTACK_RANGE);
        this.stamina = scaleOnLvl(beast.getBaseAttack(), this.level, beast.getStaminaLvlScaling());
        this.defense = scaleOnLvl(beast.getBaseDefense(), this.level, beast.getDefenseLvlScaling()) + (random.nextInt(EntityConstants.DEFENSE_RANGE * 2) - EntityConstants.DEFENSE_RANGE);
        this.ceAttacks = pickAttacks();
        this.wild = true;
    }


    public CeEntity(Beasts beast) { // dev constructor
        this.type = beast.getType();
        this.nature = Nature.getRandomNature();
        this.ceAttacks = new CeAttack[]{new CeAttack(Attacks.Punch)};
        this.hitPoints = beast.getBaseHp();
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


    public static int scaleOnLvl(int base, int lvl, int lvlScaling) {
        return base + lvl * lvlScaling;
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

    private CeAttack[] pickAttacks() {
        Random random = new Random();
        CeAttack[] pickedCeAttacks = new CeAttack[4];
        List<Attacks> typedAttacks = new ArrayList<>();
        for (Attacks attack : Attacks.values()) {
            if (attack.getType() == type) {
                typedAttacks.add(attack);
            }
        }
        for (int i = 0; i < pickedCeAttacks.length; i++) {
            int index = random.nextInt(typedAttacks.size());
            pickedCeAttacks[i] = new CeAttack(typedAttacks.remove(index));
        }

        return pickedCeAttacks;
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

    public CeAttack[] getAttacks() {
        return ceAttacks;
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

    public void dealDamage(int damage) {
        this.hitPoints -= damage;
        this.damages.add(damage);
    }
    public List<Integer> getDamages() {
        List<Integer> tmp = new ArrayList<>(damages);
        damages.clear();
        return tmp;
    }

    public void setMaxHitPoints(int maxHitPoints) {
        this.maxHitPoints = maxHitPoints;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public String toString() {
        return "CeEntity{" +
                "type=" + type +
                ", nature=" + nature +
                ", attacks=" + Arrays.toString(ceAttacks) +
                ", development=" + development +
                ", developmentLvl=" + developmentLvl +
                ", maxHitPoints=" + maxHitPoints +
                ", hitPoints=" + hitPoints +
                ", level=" + level +
                ", friendshipPoints=" + friendshipPoints +
                ", speed=" + speed +
                ", stamina=" + stamina +
                ", attack=" + attack +
                ", defense=" + defense +
                ", wild=" + wild +
                ", random=" + random +
                '}';
    }
}
