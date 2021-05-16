package com.littleBeasts.actionMenu;

import calculationEngine.entities.CeSlot;
import calculationEngine.entities.ItemNotInInventoryException;
import com.littleBeasts.entities.LitiPlayer;

import java.util.ArrayList;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.BATTLE_MENU_OFFSET;
import static config.HudConstants.BATTLE_MENU_START;

public class ItemMenu extends ActionMenu {

    public ItemMenu(CeSlot[] ceSlots) {
        super(getItemsNames(ceSlots));
        this.onConfirm(c -> {
            if (ceSlots[c].getItem() != null) {
                try {
                    LitiPlayer.instance().getCeInventory().useItem(ceSlots[c].getItem());
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

    public static ArrayList<String> getItemsNames(CeSlot[] ceSlots) {
        ArrayList<String> itemsNames = new ArrayList<>();
        for (CeSlot ceSlot : ceSlots) {
            if (ceSlot.getItem() != null)
                itemsNames.add(ceSlot.getItem().getName());
        }
        return itemsNames;
    }
}
