package com.littleBeasts.actionMenu;

import calculationEngine.entities.ItemNotInInventoryException;
import calculationEngine.environment.CeItem;
import com.littleBeasts.entities.LitiPlayer;

import java.util.ArrayList;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
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
