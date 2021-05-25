package com.littlebeasts.actionmenu;

import calculationEngine.entities.CeAttack;
import com.littlebeasts.battleanimation.AttackAnimation;
import com.littlebeasts.entities.LitiPlayer;
import com.littlebeasts.gamelogic.LitiBattle;

import java.util.ArrayList;
import java.util.List;

import static config.HudConstants.BATTLE_MENU_OFFSET;
import static config.HudConstants.BATTLE_MENU_START;

public class AttackMenu extends ActionMenu {

    public AttackMenu(List<CeAttack> ceAttacks) {
        super(getAttacksNames(ceAttacks));
        this.onConfirm(c -> {
            LitiBattle.getCeBattle().useAttack(ceAttacks.get(c));
            AttackAnimation.startMeleeAnimation(LitiPlayer.instance(), LitiBattle.getLitiBeast(), ceAttacks.get(c));
            this.setFocus(false);
        });
    }

    @Override
    protected void setX() {
        x = BATTLE_MENU_START + BATTLE_MENU_OFFSET;
    }

    private static ArrayList<String> getAttacksNames(List<CeAttack> ceAttacks) {
        ArrayList<String> attacksNames = new ArrayList<>();
        for (CeAttack ceAttack : ceAttacks) {
            attacksNames.add(ceAttack.getName());
        }
        return attacksNames;
    }
}
