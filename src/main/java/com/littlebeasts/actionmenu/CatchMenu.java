package com.littlebeasts.actionmenu;

import calculationEngine.environment.CeItem;

import java.util.ArrayList;

import static config.HudConstants.*;

public class CatchMenu extends ActionMenu {

    public CatchMenu(ArrayList<CeItem> ceItems) {
        super(getItemsNames(ceItems));
        this.onConfirm(c -> {
            System.out.println("Use Cage: " + this.items.get(c));
            this.setFocus(false);
        });
    }

    @Override
    protected void setX() {
        x = BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }

    public static ArrayList<String> getItemsNames(ArrayList<CeItem> ceItems) {
        ArrayList<String> itemsNames = new ArrayList<>();
        for (CeItem ceItem : ceItems) {
            if (ceItem != null)
                itemsNames.add(ceItem.getName());
        }
        if (itemsNames.isEmpty())
            itemsNames.add(NO_ITEMS_PLACEHOLDER);
        return itemsNames;
    }
}
