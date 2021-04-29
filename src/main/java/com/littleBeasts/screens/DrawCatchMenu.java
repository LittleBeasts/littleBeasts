package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import calculationEngine.environment.CeItem;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;
import static config.HudConstants.ITEMLISTLENGTH;
import static config.PlayerConfig.PLAYER_ACTIONS;

public class DrawCatchMenu extends DrawBattleMenu {
    private String[] items;
    public DrawCatchMenu(List<CeAttack> ceItems) {
        super();
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(2);
        this.setAmountOfDrawnItems(Math.min(2, ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItems(ceItems);
        this.setLastDrawnItem(Math.min(2, ITEMLISTLENGTH));
        setUpCatchMenuInput(ceItems);
    }

    private void setUpCatchMenuInput(List<CeAttack> ceAttacks) {
        Input.keyboard().onKeyTyped(e -> {
            if (!this.isFocused()) return;
            if (!LitiPlayer.instance().isFighting()) return;
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
                            LitiPlayer.instance().getBattle().useAttack(ceAttacks.get(this.getCurrentPosition()));
                            LitiPlayer.instance().punch();
                            break;
                        case "Catch":
                            LitiPlayer.instance().getBattle().catchBeast();
                            break;
                        default:
                            break;
                    }
                    this.confirm();
                    break;
            }
        });
    }

    private void setItems(List<CeAttack> ceItems) {
        this.items = new String[2];
        for (CeAttack ceItem : ceItems) {
            this.items[0] = "cage";
            this.items[1] = "cage2";
        }
        this.setItems(this.items);
    }
}
