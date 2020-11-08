package config;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.Attacks;

public class PlayerConfig {

    public static final String[] PLAYER_ACTIONS = new String[] {"Attack", "Catch", "Tame", "Pick Beast", "Item"};
    public static final CeAttack[] PLAYER_STANDARD_CE_ATTACKS = new CeAttack[]{new CeAttack(Attacks.Catch), new CeAttack(Attacks.Flee)};

 }
