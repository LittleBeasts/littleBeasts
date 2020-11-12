package calculationEngine.entities;

public enum Attacks {

    Punch(BeastTypes.Earth, 10, 10, 2, 3, "Punch"), //TODO: Add attacks
    Flee(BeastTypes.PlayerStandard, 0, 0, 0, 0, "Flee"),
    Catch(BeastTypes.PlayerStandard, 10, 10, 2, 3, "Catch");

    private final String name;
    private final int baseDamage;
    private final int baseAccuracy;
    private final BeastTypes type;
    private final int baseCriticalChance;
    private final int executions;


    Attacks(BeastTypes type, int baseDamage, int baseAccuracy, int baseCriticalChance, int executions, String name) {
        this.type = type;
        this.baseDamage = baseDamage;
        this.baseAccuracy = baseAccuracy;
        this.baseCriticalChance = baseCriticalChance;
        this.executions = executions;
        this.name = name;
    }

    public BeastTypes getType() {
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
