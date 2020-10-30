package calculationEngine.entities;

import java.util.Random;

public class Attack {
    private String name;
    private int damage;
    private int accuracy;
    private BeastTypes type;
    private int criticalChance;
    private int level;
    private int executions;
    private static final int maxLevel = 10;
    // probability distribution of random levels: level1:60% ; level2:30% ; level3: 10%;
    private static final int[] props = {1, 1, 1, 1, 1, 1, 2, 2, 2, 3};


    public Attack(Attacks attack) {
        this.name = attack.getName();
        this.damage = attack.getBaseDamage();
        this.accuracy = attack.getBaseAccuracy();
        this.criticalChance = attack.getBaseCriticalChance();
        this.type = attack.getType();
        this.executions = attack.getExecutions();
        this.level = calculateRandomLevel();
    }

    public Attack(String name, int damage, int accuracy, int criticalChance, BeastTypes type, int executions, int level) {
        this.name = name;
        this.damage = damage;
        this.accuracy = accuracy;
        this.criticalChance = criticalChance;
        this.type = type;
        this.level = level;
        this.executions = executions;
    }

    private int calculateRandomLevel() {
        Random random = new Random();
        return props[random.nextInt(props.length)];
    }


    public void levelUp() {
        if (level < maxLevel) {
            level++;
        }
    }
}
