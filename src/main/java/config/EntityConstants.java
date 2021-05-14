package config;

public class EntityConstants {

    public static final int MAX_LVL = 100; // Max level for player and beasts
    public static final int START_LVL = 1; // Start level for player and beasts
    public static final int LVL_RANGE = 5; // lvl range for random beast encounters
    public static final int ATTACK_RANGE = 5; // Attack range for random beast encounters
    public static final int DEFENSE_RANGE = 5; // Defense range for random beast encounters
    //------------------ attack consts------------------------------------------------------
    public static final int ATTACK_MAX_LVL = 10;
    // probability distribution of random levels: level1:60% ; level2:30% ; level3: 10%;
    public static final int[] ATTACK_PROB_DISTRIBUTION = {1, 1, 1, 1, 1, 1, 2, 2, 2, 3};

}
