package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.entities.LitiPlayer;
import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.BATTLE_MENU_OFFSET;
import static config.HudConstants.BATTLE_MENU_START;

public class AttackMenu extends ActionMenu {

    public AttackMenu(List<CeAttack> ceAttacks) {
        super(setItems(ceAttacks));
        this.onConfirm(c -> {
            LitiPlayer.instance().getBattle().useAttack(ceAttacks.get(c));
            LitiPlayer.instance().punch();
            this.setFocus(false);
        });
    }

    private static ArrayList<String> setItems(List<CeAttack> ceAttacks) {
        ArrayList<String> attacksNames = new ArrayList<>();
        for (CeAttack ceAttack : ceAttacks) {
            attacksNames.add(ceAttack.getName());
        }
        return attacksNames;
    }

    @Override
    protected int getX() {
        return BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }
}
