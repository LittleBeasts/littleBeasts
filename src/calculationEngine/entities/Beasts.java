package calculationEngine.entities;

public enum Beasts {

    FeuerFurz(BeastTypes.Fire, 100, 25,25,25,25,25,25,25,25,25, 15, "StinkenderFeuerFurz", Regions.MiscatonicMountains),
    StinkenderFeuerFurz(BeastTypes.Fire, 100, 25,25,25,25,25,25,25,25,25, Integer.MAX_VALUE, "", Regions.MiscatonicMountains);


    private BeastTypes type;
    private int baseHp;
    private int baseAttack;
    private int baseDefense;
    private int baseSpeed;
    private int baseStamina;
    private int hpLvlScaling;
    private int attackLvlScaling;
    private int defenseLvlScaling;
    private int speedLvlScaling;
    private int staminaLvlScaling;
    private int developmentlvl;
    private String developmentId;
    private Regions region;

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
