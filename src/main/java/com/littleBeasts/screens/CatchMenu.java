package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import calculationEngine.environment.CeItem;
import config.HudConstants;

import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.*;

public class CatchMenu extends ActionMenu {

    public CatchMenu(List<CeItem> ceItems) {
        super(setItems(ceItems));
        this.onConfirm(c -> {
            System.out.println("Use Cage: " + this.items.get(c));
            this.setFocus(false);
        });
    }


    private static ArrayList<String> setItems(List<CeItem> ceItems) {
        ArrayList<String> itemsNames = new ArrayList<>();
//        for (CeItem ceItem : ceItems) {
//            itemsNames.add(ceItem.getName());
//        }
        itemsNames.add("cage");
        itemsNames.add("cage2");
        return itemsNames;
    }

    @Override
    protected int getX() {
        return BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }
}
