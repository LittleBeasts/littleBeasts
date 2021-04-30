package com.littleBeasts.screens;

import calculationEngine.environment.CeItem;
import config.HudConstants;

import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.*;

public class DrawCatchMenu extends DrawBattleMenu {

    public DrawCatchMenu(List<CeItem> ceItems) {
        super(false);
        this.setItems(new ArrayList<String>());
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(2);
        this.setAmountOfDrawnItems(Math.min(2, ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItems(ceItems);
        this.setLastDrawnItem(Math.min(2, ITEMLISTLENGTH));
        this.onConfirm(c -> {
            System.out.println("Use Cage: " + this.getItems().get(c));
            this.setFocus(false);
        });
    }


    private void setItems(List<CeItem> ceItems) {
        this.addItems("cage");
        this.addItems("cage2");
    }
}
