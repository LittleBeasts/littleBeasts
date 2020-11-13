package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.entities.Player;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;

import static config.GlobalConfig.*;
import static config.HudConstants.*;
import static config.PlayerConfig.*;

public class DrawAttackMenu extends DrawBattleMenu {

    private String[] items;

    public DrawAttackMenu(CeAttack[] ceAttacks) {
        super();
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(ceAttacks.length);
        this.setAmountOfDrawnItems(Math.min(ceAttacks.length, ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItemsFromAttacks(ceAttacks);
        this.setLastDrawnItem(Math.min(ceAttacks.length, ITEMLISTLENGTH));

        setUpAttackMenuInput(ceAttacks);
    }

    private void setUpAttackMenuInput(CeAttack[] ceAttacks) {
        Input.keyboard().onKeyTyped(e -> {
            if (!this.isFocused()) return;
            if (!Player.instance().isFighting()) return;
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    Game.audio().playSound("Menu_change");
                    this.decPosition();
                    if (DEBUG_CONSOLE_OUT) System.out.println("Up | " + this.getCurrentPosition());
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    Game.audio().playSound("Menu_change");
                    this.incPosition();
                    if (DEBUG_CONSOLE_OUT) System.out.println("Down | " + this.getCurrentPosition());
                    break;
                case KeyEvent.VK_A:
                    this.confirm();
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_E:
                    Game.audio().playSound("Menu_pick");
                    if (DEBUG_CONSOLE_OUT) System.out.println(this.items[this.getCurrentPosition()]);
                    switch (PLAYER_ACTIONS[this.getCurrentPosition()]) {
                        case "Attack":
                            Player.instance().getBattle().useAttack(ceAttacks[this.getCurrentPosition()]);
                            Player.instance().punch();
                            break;
                        case "Catch":
                            Player.instance().getBattle().catchBeast();
                            break;
                        default:
                            break;
                    }
                    this.confirm();
                    break;
            }
        });
    }

    public void setItemsFromAttacks(CeAttack[] ceAttacks) {
        this.items = new String[ceAttacks.length];
        for (int i = 0; i < ceAttacks.length; i++) {
            this.items[0] = ceAttacks[i].getName();
        }
        this.setItems(this.items);
    }
}
