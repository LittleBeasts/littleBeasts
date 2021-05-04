package com.littleBeasts.screens;

import com.littleBeasts.Program;

public enum InventoryState {
    HEAD,
    NECK,
    BODY,
    HANDS,
    FEET,
    WEAPON,
    CONSUMABLES;

    public static void nextState(){
        InventoryState inventoryState = Program.getGameLogic().getDrawInventory().getInventoryState();
        switch (inventoryState){
            case HEAD:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.NECK);
                break;
            case NECK:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.BODY);
                break;
            case BODY:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.HANDS);
                break;
            case HANDS:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.FEET);
                break;
            case FEET:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.WEAPON);
                break;
            case WEAPON:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.CONSUMABLES);
                break;
            case CONSUMABLES:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.HEAD);
                break;
        }
    }

    public static void previousState(){
        InventoryState inventoryState = Program.getGameLogic().getDrawInventory().getInventoryState();
        switch (inventoryState){
            case HEAD:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.CONSUMABLES);
                break;
            case NECK:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.HEAD);
                break;
            case BODY:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.NECK);
                break;
            case HANDS:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.BODY);
                break;
            case FEET:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.HANDS);
                break;
            case WEAPON:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.FEET);
                break;
            case CONSUMABLES:
                Program.getGameLogic().getDrawInventory().setInventoryState(InventoryState.WEAPON);
                break;
        }
    }


}
