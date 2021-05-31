package config;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeAttacks;

import java.util.ArrayList;
import java.util.Arrays;

public class PlayerConstants {

    public static final ArrayList<String> PLAYER_ACTIONS = new ArrayList<>(Arrays.asList("Attack", "Catch", "Tame", "Pick Beast", "Item"));
    public static final CeAttack[] PLAYER_STANDARD_CE_ATTACKS = new CeAttack[]{new CeAttack(CeAttacks.Catch), new CeAttack(CeAttacks.Flee), new CeAttack(CeAttacks.Punch)};

 }
