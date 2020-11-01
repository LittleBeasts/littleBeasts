package calculationEngine.entities;

public class Item {
    private static int bonus; //Just a placeholder until we have a correct structure for items.

    Item(int bonus) {
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

}
