package com.littleBeasts.screens;

import com.littleBeasts.entities.Beast;

import java.awt.*;

public class BeastStats {
    private int maxHP, currentHP, x, y, width, height;
    private String name;
    private Beast littleBeast;

    public BeastStats(int x, int y, int width, int height, Beast littleBeast) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = littleBeast.getName();
        this.littleBeast = littleBeast;
    }

    public void draw(Graphics2D g) {
        g.drawImage(littleBeast.getPortrait(), x, y, width, height, null);
    }
}
