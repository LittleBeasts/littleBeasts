package com.littleBeasts.screens;

import calculationEngine.environment.CeItem;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;
import static config.HudConstants.*;

public class DrawCatchMenu extends DrawBattleMenu {

    public DrawCatchMenu(List<CeItem> ceItems) {
        super();
        this.setItems(new ArrayList<String>());
        this.setX(BATTLE_MENU_START + BATTLE_MENU_OFFSET);
        this.setAmountOfItems(2);
        this.setAmountOfDrawnItems(Math.min(2, ITEMLISTLENGTH));
        this.setHeight(HudConstants.HUD_ROW_HEIGHT * this.getAmountOfDrawnItems() / ITEMLISTLENGTH);
        this.setItems(ceItems);
        this.setLastDrawnItem(Math.min(2, ITEMLISTLENGTH));
        setUpCatchInput(ceItems);
    }

    private void setUpCatchInput(List<CeItem> ceItems) {
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
                    LitiPlayer.instance().getBattle().catchBeast();
                    if (DEBUG_CONSOLE_OUT)
                        System.out.println("Beast caught: " + LitiPlayer.instance().getBattle().catchBeast());
                    this.confirm();
                    break;
            }
        });

    }

    private void setItems(List<CeItem> ceItems) {
        this.addItems("cage");
        this.addItems("cage2");

    }
}
