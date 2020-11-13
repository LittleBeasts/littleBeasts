package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.gui.GuiComponent;

import java.awt.*;

import static config.HudConstants.*;

public class DrawInventory extends GuiComponent {

    //TODO Add Stats Class and Inventory Class when Leon did stuff and things
    private int attackStat;
    private int defenceStat;
    private int staminaStat;
    private int speedStat;
    private int maxHealth;
    private int currentHealth;
    private int level;

    public DrawInventory(){
        super(0,0,WIDTH,HEIGHT);
        System.out.println("Initialize Inventory");
        this.attackStat = Player.instance().getCePlayer().getCeEntity().getAttack();
        this.defenceStat = Player.instance().getCePlayer().getCeEntity().getDefense();
        this.speedStat = Player.instance().getCePlayer().getCeEntity().getSpeed();
        this.staminaStat = Player.instance().getCePlayer().getCeEntity().getStamina();
        this.maxHealth = Player.instance().getCePlayer().getCeEntity().getMaxHitPoints();
        this.currentHealth = Player.instance().getCePlayer().getCeEntity().getHitPoints();
        this.level = Player.instance().getCePlayer().getCeEntity().getLevel();
    }

    @Override
    public void render(Graphics2D g){
        System.out.println("draw Inventory");
        if(GameLogic.getState().equals(GameState.INVENTORY)) {
            g.setColor(Color.lightGray);
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
    }

}
