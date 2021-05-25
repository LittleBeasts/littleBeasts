package com.littlebeasts.screens;

public enum InventoryState {
    HEAD,
    NECK,
    BODY,
    HANDS,
    FEET,
    WEAPON,
    CONSUMABLES;

    public static void nextState() {
        InventoryState inventoryState = IngameScreen.getInventory().getInventoryState();
        switch (inventoryState) {
            case HEAD:
                IngameScreen.getInventory().setInventoryState(InventoryState.NECK);
                break;
            case NECK:
                IngameScreen.getInventory().setInventoryState(InventoryState.BODY);
                break;
            case BODY:
                IngameScreen.getInventory().setInventoryState(InventoryState.HANDS);
                break;
            case HANDS:
                IngameScreen.getInventory().setInventoryState(InventoryState.FEET);
                break;
            case FEET:
                IngameScreen.getInventory().setInventoryState(InventoryState.WEAPON);
                break;
            case WEAPON:
                IngameScreen.getInventory().setInventoryState(InventoryState.CONSUMABLES);
                break;
            case CONSUMABLES:
                IngameScreen.getInventory().setInventoryState(InventoryState.HEAD);
                break;
        }
    }

    public static void previousState() {
        InventoryState inventoryState = IngameScreen.getInventory().getInventoryState();
        switch (inventoryState) {
            case HEAD:
                IngameScreen.getInventory().setInventoryState(InventoryState.CONSUMABLES);
                break;
            case NECK:
                IngameScreen.getInventory().setInventoryState(InventoryState.HEAD);
                break;
            case BODY:
                IngameScreen.getInventory().setInventoryState(InventoryState.NECK);
                break;
            case HANDS:
                IngameScreen.getInventory().setInventoryState(InventoryState.BODY);
                break;
            case FEET:
                IngameScreen.getInventory().setInventoryState(InventoryState.HANDS);
                break;
            case WEAPON:
                IngameScreen.getInventory().setInventoryState(InventoryState.FEET);
                break;
            case CONSUMABLES:
                IngameScreen.getInventory().setInventoryState(InventoryState.WEAPON);
                break;
        }
    }

}
