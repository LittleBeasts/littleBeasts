package config;

import calculationEngine.entities.Attack;
import calculationEngine.entities.Attacks;

import java.util.ArrayList;

public class PlayerConfig {

    public static final String[] PLAYER_ACTIONS = new String[] {"Attack", "Catch", "Tame", "Pick Beast", "Item"};
    public static final Attack[] PLAYER_STANDARD_ATTACKS = new Attack[]{new Attack(Attacks.Catch), new Attack(Attacks.Flee)};

 }
