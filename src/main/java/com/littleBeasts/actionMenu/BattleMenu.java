package com.littleBeasts.actionMenu;

import com.littleBeasts.entities.LitiPlayer;
import config.PlayerConfig;


import java.awt.*;

import static config.HudConstants.*;

public class BattleMenu extends ActionMenu {

    private AttackMenu attackMenu;
    private CatchMenu catchMenu;

    public BattleMenu(boolean focus) {
        super(PlayerConfig.PLAYER_ACTIONS);
        createSubMenus();
        this.setFocus(focus);

        onConfirm(c -> {
            setFocus(false);
            if (c == 0) {
                getAttackMenu().setFocus(true);
            } else if (c == 1) {
                getCatchMenu().setFocus(true);
            }
        });
    }

    public void createSubMenus() {
        attackMenu = new AttackMenu(LitiPlayer.instance().getPlayerAttacks());
        catchMenu = new CatchMenu(LitiPlayer.instance().getCeInventory().getSlots());
    }

    public AttackMenu getAttackMenu() {
        return attackMenu;
    }

    public CatchMenu getCatchMenu() {
        return catchMenu;
    }

    @Override
    protected void setX() {
        x = BATTLE_MENU_START;
    }

    public void drawBattlleMenuAndFocusedMenu(Graphics2D g){
        draw(g);
        setFocus(!(getAttackMenu().isFocused() || getCatchMenu().isFocused()));
        if (getAttackMenu().isFocused()) {
            getAttackMenu().draw(g, getFirstDrawnItem());
        }
        if (getCatchMenu().isFocused()) {
            getCatchMenu().draw(g, 1 - getFirstDrawnItem());
        }
    }
}