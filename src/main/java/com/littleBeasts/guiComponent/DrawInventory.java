package com.littleBeasts.guiComponent;

import calculationEngine.entities.CeSlot;
import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.screens.InventoryState;
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

import static config.HudConstants.HEIGHT;
import static config.HudConstants.WIDTH;

public class DrawInventory extends GuiComponent {

    private static InventoryState inventoryState = InventoryState.HEAD;
    private final int ITEM_SLOT_SIZE = 150;
    //TODO Add Stats Class and Inventory Class when Leon did stuff and things
    private final int attackStat;
    private final int defenceStat;
    private final int staminaStat;
    private final int speedStat;
    private final int maxHealth;
    private final int currentHealth;
    private final int level;
    private int currentCursorPosition, indicatorPosition;
    private final int inventorySize;
    private final int maxItemsOnPage = 5;
    private int firstItem;

    public DrawInventory() {
        super(0, 0, WIDTH, HEIGHT);
        this.attackStat = LitiPlayer.instance().getCePlayer().getCeStats().getAttack();
        this.defenceStat = LitiPlayer.instance().getCePlayer().getCeStats().getDefense();
        this.speedStat = LitiPlayer.instance().getCePlayer().getCeStats().getSpeed();
        this.staminaStat = LitiPlayer.instance().getCePlayer().getCeStats().getStamina();
        this.maxHealth = LitiPlayer.instance().getCePlayer().getCeStats().getMaxHitPoints();
        this.currentHealth = LitiPlayer.instance().getCePlayer().getCeStats().getCurrentHitPoints();
        this.level = LitiPlayer.instance().getCePlayer().getCeStats().getLevel();

        Input.keyboard().onKeyTyped(KeyEvent.VK_E, e -> {
            if (Program.getGameLogic().getState() == GameState.MENU) {
                return;
            }
            if (Program.getGameLogic().getState() == GameState.INVENTORY) {
                InventoryState.nextState();
            }
        });

        Input.keyboard().onKeyTyped(KeyEvent.VK_Q, e -> {
            if (Program.getGameLogic().getState() == GameState.MENU) {
                return;
            }
            if (Program.getGameLogic().getState() == GameState.INVENTORY) {
                InventoryState.previousState();
            }
        });

        this.inventorySize = LitiPlayer.instance().getCeInventory().getSlots().length;
        this.firstItem = 0;
    }

    @Override
    public void render(Graphics2D g) {
        if (Program.getGameLogic().getState().equals(GameState.INVENTORY)) {
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

            drawPlayerStats(g);

            drawItems(g);

            //Right Side
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(5));
            g.drawLine((WIDTH / 2) + 20, HEIGHT / 3 * 2, WIDTH - 20, HEIGHT / 3 * 2);

            // rectangle bottom right corner TODO: Width and height need to be dynamic.
            g.setColor(Color.gray);
            g.fillRect((WIDTH / 2) + 20, HEIGHT / 3 * 2 + 3, 1400, 500);

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
            if (invState.equals(inventoryState) && images.get(i).equals(inventoryState.name().toLowerCase())) {
                g.drawRect(x, HEIGHT / 20, 50, 50);
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
        TextRenderer.render(g, LitiPlayer.instance().getPlayerName(), Align.CENTER_LEFT, Valign.TOP, 0, 0);
        TextRenderer.render(g, "Level: " + LitiPlayer.instance().getCePlayer().getCeStats().getLevel(), Align.CENTER_LEFT, Valign.TOP, 0, 50);
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

    private void fillItemSlotRectangles(Graphics2D g) { //TODO:Change pixel values to ratios depending on screen size.
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

    private void drawItems(Graphics2D g) {
        int itemWidth, itemHeight, leftBorderPosition, topBorderPosition, verticalGap;
        itemHeight = 75;
        itemWidth = 900;
        leftBorderPosition = 985;
        topBorderPosition = 150;
        verticalGap = 15;
        int count = 0;
        for (CeSlot ceSlot : LitiPlayer.instance().getCeInventory().getSlots()) {
            if (count >= firstItem && count < firstItem + maxItemsOnPage) {
                if (count % 2 == 0)
                    g.setColor(Color.RED);
                else
                    g.setColor(Color.magenta);
                g.drawRect(leftBorderPosition, topBorderPosition + (count - firstItem) * (verticalGap + itemHeight), itemWidth, itemHeight);

                if (count == currentCursorPosition + indicatorPosition) {
                    g.setColor(Color.BLUE);
                    g.drawRect(leftBorderPosition, topBorderPosition + (count - firstItem) * (verticalGap + itemHeight), itemWidth, itemHeight);
                }

                TextRenderer.render(g, count + 1 + ".", leftBorderPosition + 4, topBorderPosition + (count - firstItem) * (verticalGap + itemHeight) + 45);
                if (ceSlot.getItem() != null) {
                    TextRenderer.render(g, ceSlot.getItem().getName(), leftBorderPosition + 30, topBorderPosition + (count - firstItem) * (verticalGap + itemHeight) + 45);
                    if (ceSlot.getItem().isEquipped()) {
                        g.setColor(Color.YELLOW);
                        g.drawRect(leftBorderPosition, topBorderPosition + (count - firstItem) * (verticalGap + itemHeight), itemWidth, itemHeight);
                    }
                }
            }
            count++;
        }
        drawScrollBar(g);
    }

    private void drawScrollBar(Graphics2D g) {
        int startTop, height, width;
        int indicatorWidth, indicatorHeight;

        startTop = 125;
        width = 4;
        height = 500;

        indicatorWidth = 8;
        indicatorHeight = height / (inventorySize - maxItemsOnPage + 1);
        height = indicatorHeight * (inventorySize - maxItemsOnPage + 1);

        g.setColor(Color.GREEN);
        g.fillRect(WIDTH - 20, startTop, width, height);

        g.setColor(Color.BLUE);
        g.fillRect(WIDTH - 23, startTop + indicatorPosition * indicatorHeight, indicatorWidth, indicatorHeight);

    }

    public InventoryState getInventoryState() {
        return inventoryState;
    }

    public void setInventoryState(InventoryState inventoryState) {
        DrawInventory.inventoryState = inventoryState;
    }

    public void incrementCursorPosition() {
        if (currentCursorPosition < maxItemsOnPage - 1)
            currentCursorPosition++;
        else if (indicatorPosition + currentCursorPosition < inventorySize - 1) {
            indicatorPosition++;
            firstItem++;
        }
        System.out.println(currentCursorPosition + " + " + indicatorPosition + " / " + inventorySize);
    }

    public void decrementCursorPosition() {
        if (currentCursorPosition > 0) {
            currentCursorPosition--;
        } else if (indicatorPosition > 0) {
            indicatorPosition--;
            firstItem--;
        }
        System.out.println(currentCursorPosition + " + " + indicatorPosition + " / " + inventorySize);
    }
}
