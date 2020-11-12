package calculationEngine.entities;

import calculationEngine.environment.CeRegions;

public enum CeBeasts {

    FeuerFurz(CeBeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, 15, "StinkenderFeuerFurz", CeRegions.ArkhamCity),
    StinkenderFeuerFurz(CeBeastTypes.Fire, 100, 25, 25, 25, 25, 25, 25, 25, 25, 25, Integer.MAX_VALUE, "", CeRegions.ArkhamCity);


    private final CeBeastTypes type;
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
    private final CeRegions region;

    CeBeasts(CeBeastTypes type, int baseHp, int baseAttack, int baseDefense, int baseSpeed, int baseStamina, int hpLvlScaling, int attackLvlScaling, int defenseLvlScaling, int speedLvlScaling, int staminaLvlScaling, int developmentlvl, String developmentId, CeRegions region) {
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
    }

    public static CeBeasts getBeast(String name) {
        return CeBeasts.valueOf(name);
    }

    public CeRegions getRegion() {
        return region;
    }

    public CeBeastTypes getType() {
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
}
