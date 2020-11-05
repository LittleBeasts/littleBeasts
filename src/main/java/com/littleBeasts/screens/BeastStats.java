package com.littleBeasts.screens;

import com.littleBeasts.entities.Beast;
import config.HudConstants;

import java.awt.*;

public class BeastStats {
    private int maxHP, currentHP, hpRatio, x, y, width, height, portraitWidth, portraitHeight;
    private String name;
    private Beast littleBeast;

    public BeastStats(int x, int y, int width, int height, Beast littleBeast) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.portraitWidth = 100;
        this.portraitHeight = 100;

        this.name = littleBeast.getName();
        this.littleBeast = littleBeast;
    }

    public void draw(Graphics2D g) {
        g.setColor(HudConstants.BACKGROUND);
        g.fillRect(x, y, width, height);
        g.drawImage(littleBeast.getPortrait(), x, y, portraitWidth, portraitHeight, null);
        g.setColor(HudConstants.HPBARCOLOR);
        hpRatio = littleBeast.getLittleBeast().getHitPoints() * 100 / littleBeast.getLittleBeast().getMaxHitPoints();
        g.fillRect(x, y + height, portraitWidth, -(portraitHeight * hpRatio) / 100);

        g.setColor(HudConstants.TEXTCOLOR);
        String beastInfo = "";
        beastInfo += littleBeast.getMonsterName() + "\n";
        beastInfo += littleBeast.getLittleBeast().getHitPoints() + "/" + littleBeast.getLittleBeast().getMaxHitPoints() + "\n";


        drawString(g, beastInfo, x + portraitWidth, y);

    }

    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
}
