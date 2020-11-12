package calculationEngine.entities;

public enum BeastTypes {

    Fire("Water", "Earth"),
    Water("Earth", "Fire"),
    Earth("Water", "Fire"),
    PlayerStandard("NONE", "NONE");

    private final String resistanceId;
    private final String weaknessId;

    BeastTypes(String resistanceId, String weaknessId) {
        this.resistanceId = resistanceId;
        this.weaknessId = weaknessId;
    }

    public BeastTypes getResistance() {
        return BeastTypes.valueOf(this.resistanceId);
    }

    public BeastTypes getWeakness() {
        return BeastTypes.valueOf(this.weaknessId);
    }
// get modifier for type match-ups
    public double getModifier(BeastTypes beastTypes) {
        if (this.weaknessId.equals(beastTypes.name())) {
            return 0.5;
        } else if (this.resistanceId.equals(beastTypes.name())) {
            return 2.0;
        }
        return 1.0;
    }
}
