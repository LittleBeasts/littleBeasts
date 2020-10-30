package calculationEngine.entities;

public enum Attacks {

    Punch(BeastTypes.Earth, 10, 10, 2, 3, "Punch"); //TODO: Add attacks

    private BeastTypes type;
    private int baseDamage;
    private int baseAccuracy;
    private int baseCriticalChance;
    private int executions;
    private String name;


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
}
