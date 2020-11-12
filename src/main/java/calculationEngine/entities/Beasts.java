package calculationEngine.entities;

import calculationEngine.environment.Regions;

public enum Beasts {

    FeuerFurz(BeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, 15, "StinkenderFeuerFurz", Regions.MiscatonicMountains),
    StinkenderFeuerFurz(BeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, Integer.MAX_VALUE, "", Regions.MiscatonicMountains);


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
    private final int developmentlvl; // Level at which development will take place
    private final String developmentId; // ID of development of beast
    private final Regions region;

    Beasts(BeastTypes type, int baseHp, int baseAttack, int baseDefense, int baseSpeed, int baseStamina, int hpLvlScaling, int attackLvlScaling, int defenseLvlScaling, int speedLvlScaling, int staminaLvlScaling, int developmentlvl, String developmentId, Regions region) {
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
        this.developmentId = developmentId;
        this.developmentlvl = developmentlvl;
        this.region = region;
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

    public int getDevelopmentlvl() {
        return developmentlvl;
    }

    public String getDevelopmentId() {
        return developmentId;
    }
}
