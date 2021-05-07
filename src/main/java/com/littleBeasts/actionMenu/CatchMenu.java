package com.littleBeasts.actionMenu;

import calculationEngine.entities.CeSlot;
import calculationEngine.environment.CeItemTypes;

import java.util.ArrayList;

import static config.HudConstants.BATTLE_MENU_OFFSET;
import static config.HudConstants.BATTLE_MENU_START;

public class CatchMenu extends ActionMenu {

    public CatchMenu(CeSlot[] ceSlot) {
        super(getItemsNames(ceSlot));
        this.onConfirm(c -> {
            System.out.println("Use Cage: " + this.items.get(c));
            this.setFocus(false);
        });
    }

    @Override
    protected void setX() {
        x = BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }

    public static ArrayList<String> getItemsNames(CeSlot[] ceSlots) {
        ArrayList<String> itemsNames = new ArrayList<>();
        for (CeSlot ceSlot : ceSlots) {
            if (ceSlot != null && ceSlot.getItem() != null && ceSlot.getItem().getType() == CeItemTypes.cage)
                itemsNames.add(ceSlot.getItem().getName());
        }
        itemsNames.add("cage");
        itemsNames.add("cage2");
        return itemsNames;
    }
}
