package com.littleBeasts.screens;

import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiPlayer;
import config.HudConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// ToDo: rename Class
public class BeastStats {
    private int maxHP, currentHP, hpRatio, x, y, width, height, portraitWidth, portraitHeight;
    private String name;
    private LitiBeast littleBeast;
    private boolean playerBeast;
    private List<DrawDamageAnimation> damageAnimationList = new ArrayList<>();

    public BeastStats(int x, int y, int width, int height, LitiBeast littleBeast, boolean isPlayerBeast) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.playerBeast = isPlayerBeast;
        // ToDo: Get from globals, constructor should only contain positions in grid
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.portraitWidth = 100;
        this.portraitHeight = 100;

        this.name = littleBeast.getName();
        this.littleBeast = littleBeast;
    }

    public BeastStats(LitiBeast littleBeast, boolean playerBeast) {
        this.littleBeast = littleBeast;
    }

    public void draw(Graphics2D g) {
        if (this.littleBeast == null) return;
        if (playerBeast) {
            // ToDo: extract to own method, only for will remain
            g.setColor(HudConstants.BACKGROUND);
            g.fillRect(x, y, width, height);
            g.drawImage(littleBeast.getPortrait(), x, y, portraitWidth, portraitHeight, null);
            g.setColor(HudConstants.HPBARCOLOR);
            hpRatio = littleBeast.getCeEntity().getHitPoints() * 100 / littleBeast.getCeEntity().getMaxHitPoints();
            g.fillRect(x, y + height, portraitWidth, -(portraitHeight * hpRatio) / 100);

            g.setColor(HudConstants.TEXTCOLOR);
            g.setFont(new Font("Serif", Font.PLAIN, 12));
            String beastInfo = "";
            beastInfo += littleBeast.getMonsterName() + "\n";
            beastInfo += littleBeast.getCeEntity().getHitPoints() + "/" + littleBeast.getCeEntity().getMaxHitPoints() + "\n";

            drawString(g, beastInfo, x + portraitWidth, y);

            // ToDo: Add vertical wiggle in draw damage Animation
            for (Integer dmg : this.littleBeast.getCeEntity().getDamages()) {
                damageAnimationList.add(new DrawDamageAnimation((LitiPlayer.instance().getCenter()), dmg));
            }

        } else {

            for (Integer dmg : this.littleBeast.getCeEntity().getDamages()) {
                damageAnimationList.add(new DrawDamageAnimation(this.littleBeast.getCenter(), dmg));
            }

        }
        damageAnimationOutOfBounds(g);
    }

    private void damageAnimationOutOfBounds(Graphics2D g) {
        for (int i = 0; i < damageAnimationList.size(); i++) {
            damageAnimationList.get(i).draw(g);
            if (damageAnimationList.get(i).outOfBounds()) {
                damageAnimationList.set(i, null); // ToDo: maybe null is unnecessary
                damageAnimationList.remove(i);
                System.out.println("Removed");
            }
        }
    }

    // Todo: Remove and call equivalent from HUD-Class
    private static void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }

    public void drawDamageRolls(Graphics2D g) {
        damageAnimationOutOfBounds(g);
    }

    public boolean isReadyToBeRemoved() {
        return this.damageAnimationList.isEmpty();
    }
}
