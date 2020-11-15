package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.resources.Resources;

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
    private final int ITEM_SLOT_SIZE = 150;

    public DrawInventory() {
        super(0, 0, WIDTH, HEIGHT);
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
    public void render(Graphics2D g) {
        System.out.println("draw Inventory");
        if (GameLogic.getState().equals(GameState.INVENTORY)) {
            g.setColor(Color.gray);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            //line in the middle
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine((WIDTH / 2), 0, (WIDTH / 2), HEIGHT);

            //Item slots
            fillItemSlotRectangles(g);
            drawItemSlotBorders(g);

            //Player sprite
            g.setColor(Color.lightGray);
            g.fillRect((WIDTH / 4) - 75, HEIGHT - 610, 150, 330);
            ImageRenderer.renderScaled(g, Resources.images().get("sprites/playerInventoryDisplay.png"),
                    (WIDTH / 4) - 75, HEIGHT - 610, 11.7);

            //Player Stats
            g.fillRect((WIDTH / 4) + WIDTH / 16, HEIGHT - 900, 300, 140);
            g.setColor(Color.BLACK);
            g.drawRect((WIDTH / 4) + WIDTH / 16, HEIGHT - 900, 300, 140);
            Font font = new Font(g.getFont().getName(), Font.BOLD, 30);
            g.setFont(font);
            TextRenderer.render(g, Player.instance().getPlayerName(), Align.CENTER_LEFT, Valign.TOP, 0, 0);
            TextRenderer.render(g, "Level: " + Integer.toString(Player.instance().getCePlayer().getCeEntity().getLevel()), Align.CENTER_LEFT, Valign.TOP, 0, 50);
            font = new Font(g.getFont().getName(), Font.BOLD, 22);
            g.setFont(font);
            TextRenderer.render(g, "Hitpoints: " + this.currentHealth + " / " + this.maxHealth, (WIDTH / 4) + WIDTH / 16 + 5, HEIGHT - 875, true);
            TextRenderer.render(g, "Attack: " + this.maxHealth, (WIDTH / 4) + WIDTH / 16 + 5, HEIGHT - 850, true);
            TextRenderer.render(g, "Defence: " + this.maxHealth, (WIDTH / 4) + WIDTH / 16 + 5, HEIGHT - 825, true);
            TextRenderer.render(g, "Stamina: " + this.maxHealth, (WIDTH / 4) + WIDTH / 16 + 5, HEIGHT - 800, true);
            TextRenderer.render(g, "Speed: " + this.maxHealth, (WIDTH / 4) + WIDTH / 16 + 5, HEIGHT - 775, true);


        }
    }

    private void drawItemSlotBorders(Graphics2D g) {
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        //Head
        g.drawRect((WIDTH / 4) - 75, HEIGHT - 790, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Neck
        g.drawRect((WIDTH / 4) - 300, HEIGHT - 610, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Hands
        g.drawRect((WIDTH / 4) - 300, HEIGHT - 430, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Body
        g.drawRect((WIDTH / 4) + 150, HEIGHT - 610, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Weapon
        g.drawRect((WIDTH / 4) + 150, HEIGHT - 430, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Feet
        g.drawRect((WIDTH / 4) - 75, HEIGHT - 250, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
    }

    private void fillItemSlotRectangles(Graphics2D g) {
        g.setColor(Color.lightGray);
        //Head
        g.fillRect((WIDTH / 4) - 75, HEIGHT - 790, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Neck
        g.fillRect((WIDTH / 4) - 300, HEIGHT - 610, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Hands
        g.fillRect((WIDTH / 4) - 300, HEIGHT - 430, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Body
        g.fillRect((WIDTH / 4) + 150, HEIGHT - 610, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Weapon
        g.fillRect((WIDTH / 4) + 150, HEIGHT - 430, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
        //Feet
        g.fillRect((WIDTH / 4) - 75, HEIGHT - 250, ITEM_SLOT_SIZE, ITEM_SLOT_SIZE);
    }

}
