package com.littleBeasts.screens;

import com.littleBeasts.entities.LitiPlayer;
import config.PlayerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static config.HudConstants.*;

/*--------------------------------------------
This class creates a menu for use in battle.
It is called in the HUD class.
Height, width and bottom pad should be adjusted in HudConstants

20201105 D.B. Created Class
--------------------------------------------*/

public class BattleMenu extends ActionMenu { // dev constructor

    private boolean focus;
    private final List<Consumer<Integer>> confirmConsumer;



    private AttackMenu attackMenu;
    private CatchMenu catchMenu;


    public BattleMenu(boolean focus) {
        super(PlayerConfig.PLAYER_ACTIONS);
        createSubMenus();
        this.setFocus(focus);
        this.confirmConsumer = new CopyOnWriteArrayList<>();
        setUpMenuInput(this.items);

    }

    private void setUpMenuInput(ArrayList<String> items) {
    }

    public void createSubMenus() {
        attackMenu = new AttackMenu(LitiPlayer.instance().getPlayerAttacks());
        catchMenu = new CatchMenu(LitiPlayer.instance().getPlayerItems());
    }


    // get events from constructor


    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public int getCurrentFocus() {
        return currentPosition;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public boolean isFocused() {
        return this.focus;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public AttackMenu getAttackMenu() {
        return attackMenu;
    }

    public CatchMenu getCatchMenu() {
        return catchMenu;
    }
    @Override
    protected int getX() {
        return BATTLE_MENU_START;
    }

    public void addItems(String item) {
        this.items.add(item);
    }

    public void setAmountOfDrawnItems(int amountOfDrawnItems) {
        this.amountOfDrawnItems = amountOfDrawnItems;
    }

    public int getAmountOfDrawnItems() {
        return amountOfDrawnItems;
    }

    public void setLastDrawnItem(int lastDrawnItem) {
        this.lastDrawnItem = lastDrawnItem;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAmountOfItems(int amountOfItems) {
        this.amountOfItems = amountOfItems;
    }


}
