package com.littleBeasts.screens;

import com.littleBeasts.GameLogic;
import com.littleBeasts.GameState;
import com.littleBeasts.entities.Player;
import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.GuiComponent;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    private static InventoryState inventoryState = InventoryState.HEAD;

    public DrawInventory() {
        super(0, 0, WIDTH, HEIGHT);
        this.attackStat = Player.instance().getCePlayer().getCeEntity().getAttack();
        this.defenceStat = Player.instance().getCePlayer().getCeEntity().getDefense();
        this.speedStat = Player.instance().getCePlayer().getCeEntity().getSpeed();
        this.staminaStat = Player.instance().getCePlayer().getCeEntity().getStamina();
        this.maxHealth = Player.instance().getCePlayer().getCeEntity().getMaxHitPoints();
        this.currentHealth = Player.instance().getCePlayer().getCeEntity().getHitPoints();
        this.level = Player.instance().getCePlayer().getCeEntity().getLevel();

        Input.keyboard().onKeyTyped(KeyEvent.VK_E, e -> {
            if (GameLogic.getState() == GameState.MENU) {
                return;
            }
            if (GameLogic.getState() == GameState.INVENTORY) {
                DrawInventory.nextState();
            }
        });

        Input.keyboard().onKeyTyped(KeyEvent.VK_Q, e -> {
            if (GameLogic.getState() == GameState.MENU) {
                return;
            }
            if (GameLogic.getState() == GameState.INVENTORY) {
                DrawInventory.previousState();
            }
        });
    }

    @Override
    public void render(Graphics2D g) {
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
            drawPlayerStats(g);

            //Right Side
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine((WIDTH / 2) + 20, HEIGHT / 3 * 2, WIDTH - 20, HEIGHT / 3 * 2);

            //Categories
            drawCategories(g);



        }
    }

    private void drawCategories(Graphics2D g) {
        ArrayList<String> images = new ArrayList<>();
        images.add("head");
        images.add("neck");
        images.add("body");
        images.add("hands");
        images.add("feet");
        images.add("weapon");
        images.add("consumables");
        int x = (WIDTH / 2) + 80;
        int widthOfTab = ((WIDTH / 2) - 100) / 7;
        int i = 0;
        g.setStroke(new BasicStroke(2));
        for (InventoryState invState : InventoryState.values()) {
            if(invState.equals(inventoryState) && images.get(i).equals(inventoryState.name().toLowerCase())){
                g.drawRect(x, HEIGHT/20, 50, 50);
            }
            ImageRenderer.renderScaled(g, Resources.images().get("sprites/" + images.get(i) + ".png"), x, HEIGHT / 20, 0.1);
            x = x + widthOfTab;
            i++;
        }
    }

    private void drawPlayerStats(Graphics2D g) {
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


    public static void nextState(){
        switch (inventoryState){
            case HEAD:
                inventoryState = InventoryState.NECK;
                break;
            case NECK:
                inventoryState = InventoryState.BODY;
                break;
            case BODY:
                inventoryState = InventoryState.HANDS;
                break;
            case HANDS:
                inventoryState = InventoryState.FEET;
                break;
            case FEET:
                inventoryState = InventoryState.WEAPON;
                break;
            case WEAPON:
                inventoryState = InventoryState.CONSUMABLES;
                break;
            case CONSUMABLES:
                inventoryState = InventoryState.HEAD;
                break;
        }
    }

    public static void previousState(){
        switch (inventoryState){
            case HEAD:
                inventoryState = InventoryState.CONSUMABLES;
                break;
            case NECK:
                inventoryState = InventoryState.HEAD;
                break;
            case BODY:
                inventoryState = InventoryState.NECK;
                break;
            case HANDS:
                inventoryState = InventoryState.BODY;
                break;
            case FEET:
                inventoryState = InventoryState.HANDS;
                break;
            case WEAPON:
                inventoryState = InventoryState.FEET;
                break;
            case CONSUMABLES:
                inventoryState = InventoryState.WEAPON;
                break;
        }
    }

}
