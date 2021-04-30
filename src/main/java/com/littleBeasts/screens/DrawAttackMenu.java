package com.littleBeasts.screens;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;
import static config.PlayerConfig.PLAYER_ACTIONS;

public class DrawAttackMenu extends DrawBattleMenu {


    public DrawAttackMenu(List<CeAttack> ceAttacks) {
        super();
        this.setItems(new ArrayList<>());
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(ceAttacks.size());
        this.setAmountOfDrawnItems(Math.min(ceAttacks.size(), ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItemsFromAttacks(ceAttacks);
        this.setLastDrawnItem(Math.min(ceAttacks.size(), ITEMLISTLENGTH));

        setUpAttackMenuInput(ceAttacks);
    }

    private void setUpAttackMenuInput(List<CeAttack> ceAttacks) {
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
                    if (DEBUG_CONSOLE_OUT) System.out.println(this.getItems().get(this.getCurrentPosition()));
                    switch (PLAYER_ACTIONS.get(this.getCurrentPosition())) {
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

    public void setItemsFromAttacks(List<CeAttack> ceAttacks) {
        for (CeAttack ceAttack : ceAttacks) {
            this.addItems(ceAttack.getName());
        }
    }
}
