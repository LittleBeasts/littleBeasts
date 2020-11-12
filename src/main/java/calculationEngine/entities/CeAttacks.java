package calculationEngine.entities;

public enum CeAttacks {

    Punch(CeBeastTypes.Fire, 10, 10, 2, 3, "Punch"), //TODO: Add attacks
    Flee(CeBeastTypes.PlayerStandard, 0, 0, 0, 0, "Flee"),
    Catch(CeBeastTypes.PlayerStandard, 10, 10, 2, 3, "Catch");

    private final String name;
    private final int baseDamage;
    private final int baseAccuracy;
    private final CeBeastTypes type;
    private final int baseCriticalChance;
    private final int executions;


    CeAttacks(CeBeastTypes type, int baseDamage, int baseAccuracy, int baseCriticalChance, int executions, String name) {
        this.type = type;
        this.baseDamage = baseDamage;
        this.baseAccuracy = baseAccuracy;
        this.baseCriticalChance = baseCriticalChance;
        this.executions = executions;
        this.name = name;
    }

    public CeBeastTypes getType() {
        return type;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public int getBaseAccuracy() {
        return baseAccuracy;
    }

    public int getBaseCriticalChance() {
        return baseCriticalChance;
    }

    public int getExecutions() {
        return executions;
    }

    public String getName() {
        return name;
    }
}
