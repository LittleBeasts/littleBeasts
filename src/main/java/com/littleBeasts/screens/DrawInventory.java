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
            g.setColor(Color.gray);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            //line in the middle
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine((WIDTH/2),0,(WIDTH/2), HEIGHT );

            //Item slots
            fillItemSlotRectangles(g);
            drawItemSlotBorders(g);

            //Player sprite
            g.drawRect((WIDTH/4)-75, HEIGHT-610, 150, 330);

        }
    }

    private void drawItemSlotBorders(Graphics2D g) {
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        //Head
        g.drawRect((WIDTH/4)-75, HEIGHT-790, 150, 150);
        //Neck
        g.drawRect((WIDTH/4)-300, HEIGHT-610, 150, 150);
        //Hands
        g.drawRect((WIDTH/4)-300, HEIGHT-430, 150, 150);
        //Body
        g.drawRect((WIDTH/4)+150, HEIGHT-610, 150, 150);
        //Weapon
        g.drawRect((WIDTH/4)+150, HEIGHT-430, 150, 150);
        //Feet
        g.drawRect((WIDTH/4)-75, HEIGHT-250, 150, 150);
    }

    private void fillItemSlotRectangles(Graphics2D g) {
        g.setColor(Color.lightGray);
        //Head
        g.fillRect((WIDTH/4)-75, HEIGHT-790, 150, 150);
        //Neck
        g.fillRect((WIDTH/4)-300, HEIGHT-610, 150, 150);
        //Hands
        g.fillRect((WIDTH/4)-300, HEIGHT-430, 150, 150);
        //Body
        g.fillRect((WIDTH/4)+150, HEIGHT-610, 150, 150);
        //Weapon
        g.fillRect((WIDTH/4)+150, HEIGHT-430, 150, 150);
        //Feet
        g.fillRect((WIDTH/4)-75, HEIGHT-250, 150, 150);
    }

}
