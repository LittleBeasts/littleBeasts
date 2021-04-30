package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;

import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.*;

public class DrawAttackMenu extends DrawBattleMenu {


    public DrawAttackMenu(List<CeAttack> ceAttacks) {
        super(false);
        this.setItems(new ArrayList<>());
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(ceAttacks.size());
        this.setAmountOfDrawnItems(Math.min(ceAttacks.size(), ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItemsFromAttacks(ceAttacks);
        this.setLastDrawnItem(Math.min(ceAttacks.size(), ITEMLISTLENGTH));
        this.onConfirm(c -> {
            LitiPlayer.instance().getBattle().useAttack(ceAttacks.get(c));
            this.setFocus(false);
        });
    }


    public void setItemsFromAttacks(List<CeAttack> ceAttacks) {
        for (CeAttack ceAttack : ceAttacks) {
            this.addItems(ceAttack.getName());
        }
    }
}
