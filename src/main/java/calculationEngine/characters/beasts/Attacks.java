package calculationEngine.characters.beasts;

import java.util.ArrayList;
import java.util.List;

public enum Attacks {

    Punch(BeastTypes.Earth, 10, 10, 2, "Punch");

    private BeastTypes type;
    private int baseDamage;
    private int baseAccuracy;
    private int baseCriticalChance;
    private String name;


    Attacks(BeastTypes type, int baseDamage, int baseAccuracy, int baseCriticalChance, String name) {
        this.type = type;
        this.baseDamage = baseDamage;
        this.baseAccuracy = baseAccuracy;
        this.baseCriticalChance = baseCriticalChance;
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


}
