package com.littleBeasts.screens;

import com.littleBeasts.entities.Beast;
import com.littleBeasts.entities.Player;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BeastStats {
    private int maxHP, currentHP, hpRatio, x, y, width, height, portraitWidth, portraitHeight;
    private String name;
    private Beast littleBeast;
    private boolean playerBeast;
    private List<DamageAnimation> damageAnimationList = new ArrayList<>();

    public BeastStats(int x, int y, int width, int height, Beast littleBeast, boolean playerBeast) {
        this.maxHP = maxHP;
        this.currentHP = currentHP;
        this.playerBeast = playerBeast;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.portraitWidth = 100;
        this.portraitHeight = 100;

        this.name = littleBeast.getName();
        this.littleBeast = littleBeast;
    }

    public BeastStats(Beast littleBeast, boolean playerBeast) {
        this.littleBeast = littleBeast;
    }

    public void draw(Graphics2D g) {
        if (this.littleBeast == null) return;
        if (playerBeast) {
            g.setColor(HudConstants.BACKGROUND);
            g.fillRect(x, y, width, height);
            g.drawImage(littleBeast.getPortrait(), x, y, portraitWidth, portraitHeight, null);
            g.setColor(HudConstants.HPBARCOLOR);
            hpRatio = littleBeast.getLittleBeast().getHitPoints() * 100 / littleBeast.getLittleBeast().getMaxHitPoints();
            g.fillRect(x, y + height, portraitWidth, -(portraitHeight * hpRatio) / 100);

            g.setColor(HudConstants.TEXTCOLOR);
            g.setFont(new Font("Serif", Font.PLAIN, 12));
            String beastInfo = "";
            beastInfo += littleBeast.getMonsterName() + "\n";
            beastInfo += littleBeast.getLittleBeast().getHitPoints() + "/" + littleBeast.getLittleBeast().getMaxHitPoints() + "\n";

            drawString(g, beastInfo, x + portraitWidth, y);


            for (Integer dmg : this.littleBeast.getLittleBeast().getDamages()) {
                damageAnimationList.add(new DamageAnimation((Player.instance().getCenter()), dmg));
            }

            damageAnimationOutOfBounds(g);

        } else {

            for (Integer dmg : this.littleBeast.getLittleBeast().getDamages()) {
                damageAnimationList.add(new DamageAnimation(this.littleBeast.getCenter(), dmg));
            }

            damageAnimationOutOfBounds(g);
        }
    }

    private void damageAnimationOutOfBounds(Graphics2D g) {
        for (int i = 0; i < damageAnimationList.size(); i++) {
            damageAnimationList.get(i).draw(g);
            if (damageAnimationList.get(i).outOfBounds()) {
                damageAnimationList.get(i).equals(null);
                damageAnimationList.remove(i);
                System.out.println("Removed");
            }
        }
    }

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
