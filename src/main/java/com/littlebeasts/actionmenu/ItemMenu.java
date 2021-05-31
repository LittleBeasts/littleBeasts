package com.littlebeasts.actionmenu;

import calculationEngine.entities.ItemNotInInventoryException;
import calculationEngine.environment.CeItem;
import com.littlebeasts.entities.LitiPlayer;

import java.util.ArrayList;

import static config.GlobalConstants.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;

public class ItemMenu extends ActionMenu {

    public ItemMenu(ArrayList<CeItem> ceItems) {
        super(getItemsNames(ceItems));
        this.onConfirm(c -> {
            if (!ceItems.isEmpty() && ceItems.get(c) != null) {
                try {
                    LitiPlayer.instance().getCeInventory().useItem(ceItems.get(c));
                } catch (ItemNotInInventoryException e) {
                    // handle exception
                    e.printStackTrace();
                }
            }
            if (DEBUG_CONSOLE_OUT)
                System.out.println("Use item: " + this.items.get(c));
            this.setFocus(false);
        });
    }

    @Override
    protected void setX() {
        x = BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }

}
