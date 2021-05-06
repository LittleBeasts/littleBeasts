package com.littleBeasts.actionMenu;

import calculationEngine.environment.CeItem;

import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.*;

public class CatchMenu extends ActionMenu {

    public CatchMenu(List<CeItem> ceItems) {
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

    public static ArrayList<String> getItemsNames(List<CeItem> ceItems) {
        ArrayList<String> itemsNames = new ArrayList<>();
//        for (CeItem ceItem : ceItems) {
//            itemsNames.add(ceItem.getName());
//        }
        itemsNames.add("cage");
        itemsNames.add("cage2");
        return itemsNames;
    }
}
