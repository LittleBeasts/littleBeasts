package com.littleBeasts.screens;

import com.littleBeasts.entities.LitiPlayer;
import config.PlayerConfig;


import static config.HudConstants.*;

public class BattleMenu extends ActionMenu {

    private AttackMenu attackMenu;
    private CatchMenu catchMenu;

    public BattleMenu(boolean focus) {
        super(PlayerConfig.PLAYER_ACTIONS);
        createSubMenus();
        this.setFocus(focus);
    }

    public void createSubMenus() {
        attackMenu = new AttackMenu(LitiPlayer.instance().getPlayerAttacks());
        catchMenu = new CatchMenu(LitiPlayer.instance().getPlayerItems());
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
}