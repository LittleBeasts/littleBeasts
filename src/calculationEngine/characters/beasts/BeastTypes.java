package calculationEngine.characters.beasts;

public enum BeastTypes {

    Fire("Water", "Earth"),
    Water("Earth", "Fire"),
    Earth("Water", "Fire");

    private String resistanceId;
    private String weaknessId;

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
}
