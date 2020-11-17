package com.littleBeasts.screens;

public enum InventoryState {
    HEAD,
    NECK,
    BODY,
    HANDS,
    FEET,
    WEAPON,
    CONSUMABLES;

    public static void nextState(){
        InventoryState inventoryState = DrawInventory.getInventoryState();
        switch (inventoryState){
            case HEAD:
                DrawInventory.setInventoryState(InventoryState.NECK);
                break;
            case NECK:
                DrawInventory.setInventoryState(InventoryState.BODY);
                break;
            case BODY:
                DrawInventory.setInventoryState(InventoryState.HANDS);
                break;
            case HANDS:
                DrawInventory.setInventoryState(InventoryState.FEET);
                break;
            case FEET:
                DrawInventory.setInventoryState(InventoryState.WEAPON);
                break;
            case WEAPON:
                DrawInventory.setInventoryState(InventoryState.CONSUMABLES);
                break;
            case CONSUMABLES:
                DrawInventory.setInventoryState(InventoryState.HEAD);
                break;
        }
    }

    public static void previousState(){
        InventoryState inventoryState = DrawInventory.getInventoryState();
        switch (inventoryState){
            case HEAD:
                DrawInventory.setInventoryState(InventoryState.CONSUMABLES);
                break;
            case NECK:
                DrawInventory.setInventoryState(InventoryState.HEAD);
                break;
            case BODY:
                DrawInventory.setInventoryState(InventoryState.NECK);
                break;
            case HANDS:
                DrawInventory.setInventoryState(InventoryState.BODY);
                break;
            case FEET:
                DrawInventory.setInventoryState(InventoryState.HANDS);
                break;
            case WEAPON:
                DrawInventory.setInventoryState(InventoryState.FEET);
                break;
            case CONSUMABLES:
                DrawInventory.setInventoryState(InventoryState.WEAPON);
                break;
        }
    }


}
