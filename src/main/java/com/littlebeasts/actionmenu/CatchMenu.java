package com.littlebeasts.actionmenu;

import calculationEngine.environment.CeItem;

import java.util.ArrayList;

import static config.HudConstants.BATTLE_MENU_OFFSET;
import static config.HudConstants.BATTLE_MENU_START;

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

}
