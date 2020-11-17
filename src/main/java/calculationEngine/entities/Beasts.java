package calculationEngine.entities;

import calculationEngine.environment.Regions;

public enum Beasts {

    FeuerFurz(BeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, 15, "StinkenderFeuerFurz", Regions.MiscatonicMountains, "sprites/icon.png"),
    StinkenderFeuerFurz(BeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, Integer.MAX_VALUE, "", Regions.MiscatonicMountains, "sprites/icon.png");


    private final BeastTypes type;
    private final int baseHp;
    private final int baseAttack;
    private final int baseDefense;
    private final int baseSpeed;
    private final int baseStamina;
    private final int hpLvlScaling;
    private final int attackLvlScaling;
    private final int defenseLvlScaling;
    private final int speedLvlScaling;
    private final int staminaLvlScaling;
    private final int evolutionlvl; // Level at which development will take place
    private final String evolutionId; // ID of development of beast
    private final Regions region;
    private final String portrait;

    Beasts(BeastTypes type, int baseHp, int baseAttack, int baseDefense, int baseSpeed, int baseStamina, int hpLvlScaling, int attackLvlScaling, int defenseLvlScaling, int speedLvlScaling, int staminaLvlScaling, int developmentlvl, String developmentId, Regions region, String portrait) {
        this.type = type;
        this.baseHp = baseHp;
        this.baseAttack = baseAttack;
        this.baseDefense = baseDefense;
        this.baseSpeed = baseSpeed;
        this.baseStamina = baseStamina;
        this.hpLvlScaling = hpLvlScaling;
        this.attackLvlScaling = attackLvlScaling;
        this.defenseLvlScaling = defenseLvlScaling;
        this.speedLvlScaling = speedLvlScaling;
        this.staminaLvlScaling = staminaLvlScaling;
        this.evolutionId = developmentId;
        this.evolutionlvl = developmentlvl;
        this.region = region;
        this.portrait = portrait;
    }

    public static Beasts getBeast(String name) {
        return Beasts.valueOf(name);
    }

    public Regions getRegion() {
        return region;
    }

    public BeastTypes getType() {
        return type;
    }

    public int getBaseHp() {
        return baseHp;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public int getBaseDefense() {
        return baseDefense;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }

    public int getBaseStamina() {
        return baseStamina;
    }

    public int getHpLvlScaling() {
        return hpLvlScaling;
    }

    public int getAttackLvlScaling() {
        return attackLvlScaling;
    }

    public int getDefenseLvlScaling() {
        return defenseLvlScaling;
    }

    public int getSpeedLvlScaling() {
        return speedLvlScaling;
    }

    public int getStaminaLvlScaling() {
        return staminaLvlScaling;
    }

    public int getEvolutionlvl() {
        return evolutionlvl;
    }

    public String getEvolutionId() {
        return evolutionId;
    }
    public String getPortrait() {
        return portrait;
    }
}
