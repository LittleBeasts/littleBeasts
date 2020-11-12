package calculationEngine.entities;

public enum CeBeastTypes {

    Fire("Water", "Earth"),
    Water("Earth", "Fire"),
    Earth("Water", "Fire"),
    PlayerStandard("NONE", "NONE");

    private final String resistanceId;
    private final String weaknessId;

    CeBeastTypes(String resistanceId, String weaknessId) {
        this.resistanceId = resistanceId;
        this.weaknessId = weaknessId;
    }

    public CeBeastTypes getResistance() {
        return CeBeastTypes.valueOf(this.resistanceId);
    }

    public CeBeastTypes getWeakness() {
        return CeBeastTypes.valueOf(this.weaknessId);
    }
// get modifier for type match-ups
    public double getModifier(CeBeastTypes beastTypes) {
        if (this.weaknessId.equals(beastTypes.name())) {
            return 0.5;
        } else if (this.resistanceId.equals(beastTypes.name())) {
            return 2.0;
        }
        return 1.0;
    }
}
