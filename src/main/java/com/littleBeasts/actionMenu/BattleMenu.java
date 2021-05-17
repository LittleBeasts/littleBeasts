package com.littleBeasts.actionMenu;

import com.littleBeasts.entities.LitiPlayer;
import config.PlayerConfig;


import java.awt.*;

import static config.HudConstants.*;

public class BattleMenu extends ActionMenu {

    private AttackMenu attackMenu;
    private CatchMenu catchMenu;
    private ItemMenu itemMenu;

    public BattleMenu(boolean focus) {
        super(PlayerConfig.PLAYER_ACTIONS);
        createSubMenus();
        this.setFocus(focus);

        onConfirm(c -> {
            setFocus(false);
            if (c == PlayerConfig.PLAYER_ACTIONS.indexOf("Attack")) {
                getAttackMenu().setFocus(true);
            } else if (c == PlayerConfig.PLAYER_ACTIONS.indexOf("Catch")) {
                getCatchMenu().setFocus(true);
            } else if (c == PlayerConfig.PLAYER_ACTIONS.indexOf("Item")) {
                getItemMenu().setFocus(true);
            }
        });
    }

    public void createSubMenus() {
        attackMenu = new AttackMenu(LitiPlayer.instance().getPlayerAttacks());
        catchMenu = new CatchMenu(LitiPlayer.instance().getCeInventory().getSlots());
        itemMenu = new ItemMenu(LitiPlayer.instance().getCeInventory().getSlots());
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

    public void drawBattleMenuAndFocusedMenu(Graphics2D g){
        draw(g);
        setFocus(!(getAttackMenu().isFocused() || getCatchMenu().isFocused() || getItemMenu().isFocused()));
        if (getAttackMenu().isFocused()) {
            getAttackMenu().draw(g, firstDrawnItem);
        }
        if (getCatchMenu().isFocused()) {
            getCatchMenu().draw(g, 1 - firstDrawnItem);
        }
        if (getItemMenu().isFocused()) {
            getItemMenu().draw(g, 4 - firstDrawnItem);
        }
    }

    public ItemMenu getItemMenu() {
        return itemMenu;
    }
}