package calculationEngine.entities;

import java.util.Random;

public class CeAttack {
    private String name;
    private int damage;
    private int accuracy;
    private BeastTypes type;
    private int criticalChance;
    private int level;
    private int executions;
    private static final int maxLevel = 10;
    // probability distribution of random levels: level1:60% ; level2:30% ; level3: 10%;
    private static final int[] probabilityDistribution = {1, 1, 1, 1, 1, 1, 2, 2, 2, 3};

    public CeAttack(Attacks attack) {
        this.name = attack.getName();
        this.damage = attack.getBaseDamage();
        this.accuracy = attack.getBaseAccuracy();
        this.criticalChance = attack.getBaseCriticalChance();
        this.type = attack.getType();
        this.executions = attack.getExecutions();
        this.level = calculateRandomLevel();
        scaleStats();
    }

    public CeAttack(String name, int damage, int accuracy, int criticalChance, BeastTypes type, int executions, int level) {
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
        return probabilityDistribution[random.nextInt(probabilityDistribution.length)];
    }


    public void levelUp() {
        if (this.level < maxLevel) {
            this.level++;
        }
        scaleStats();
    }

    private void scaleStats() {
        //e.g. level3 increases stats by 30%
        double factor = 1 + this.level * 0.1;
        this.damage = (int) Math.round(this.damage * factor);
        this.accuracy = (int) Math.round(this.accuracy * factor);
        this.criticalChance = (int) Math.round(this.criticalChance * factor);
    }

    public void incrementExecution(int executions) {
        this.executions++;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public void setCriticalChance(int criticalChance) {
        this.criticalChance = criticalChance;
    }

    public String getName() {
        return this.name;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAccuracy() {
        return this.accuracy;
    }

    public BeastTypes getType() {
        return this.type;
    }

    public int getCriticalChance() {
        return this.criticalChance;
    }

    public int getLevel() {
        return this.level;
    }

    public int getExecutions() {
        return this.executions;
    }
}
