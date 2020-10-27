package calculationEngine.characters.beasts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Beast {
// |--------------------------------------------------------------------------|
// |    This class is the parent class for every littleBeast.                 |
// |    Every Beast will inherit the props and funcs of this class and        |
// |    extend them.                                                          |
// |--------------------------------------------------------------------------|

    // properties
    private BeastTypes type;
    private Nature nature;
    private Beast development;
    private Attack[] attacks;

    // stats
    private int hitPoints;
    private int level;
    private int friendshipPoints;
    private int speed;
    private int stamina;
    private int attack;
    private int defense;

    public Beast(BeastTypes type, Nature nature, Attack[] attacks, int hitPoints, int level, int friendshipPoints, int speed, int stamina, int attack, int defense) {
        this.type = type;
        this.nature = nature;
        this.attacks = attacks;
        this.hitPoints = hitPoints;
        this.level = level;
        this.friendshipPoints = friendshipPoints;
        this.speed = speed;
        this.stamina = stamina;
        this.attack = attack;
        this.defense = defense;
    }

    public Beast(BeastTypes type, int speed, int stamina, int attack, int defense) {
        Random random = new Random();

        this.hitPoints = calcHP();

        Nature[] natures = Nature.values();
        this.nature = natures[random.nextInt(natures.length)];

        this.type = type;
        this.level = 1; // TODO: Change to calc level based on Player Level / Story Progress
        this.speed = speed;
        this.attack = attack;
        this.stamina = stamina;
        this.defense = defense;
        this.attacks = pickAttacks();
    }

    private Attack[] pickAttacks() {
        Random random = new Random();
        Attack[] pickedAttacks = new Attack[4];
        List<Attacks> typedAttacks = new ArrayList<>();
        for (Attacks attack: Attacks.values()) {
            if(attack.getType() == type){
                typedAttacks.add(attack);
            }
        }
        for (int i = 0; i < pickedAttacks.length; i++) {
            pickedAttacks[i] = new Attack(typedAttacks.get(random.nextInt(typedAttacks.size())));
        }

        return pickedAttacks;
    }

    private int calcHP() {
        // TODO: Calc HP based on lvl
        return 100;
    }

    public BeastTypes getType() {
        return type;
    }

    public Nature getNature() {
        return nature;
    }

    public Beast getDevelopment() {
        return development;
    }

    public Attack[] getAttacks() {
        return attacks;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getLevel() {
        return level;
    }

    public int getFriendshipPoints() {
        return friendshipPoints;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStamina() {
        return stamina;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setType(BeastTypes type) {
        this.type = type;
    }

    public void setDevelopment(Beast development) {
        this.development = development;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
