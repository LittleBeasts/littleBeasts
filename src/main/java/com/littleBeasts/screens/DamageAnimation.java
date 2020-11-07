package com.littleBeasts.screens;

import config.HudConstants;
import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public class DamageAnimation {
    private Point2D point2D;
    private int damage, speed, increment, fadeoutSpeed;


    public DamageAnimation(Point2D point2D, int damage) {
        this.point2D = new Point2D.Double(point2D.getX() * Game.graphics().getBaseRenderScale(), point2D.getY() * Game.graphics().getBaseRenderScale());
        this.increment = 0;
        this.damage = damage;
        this.speed = 4;
        this.fadeoutSpeed = 2;

    }

    public void draw(Graphics2D g) {
        g.setFont(HudConstants.ChatWindowFont);
        g.setColor(new Color(255, 0, 0, (Math.max((int) (255 - (fadeoutSpeed * increment++)), 0))));

        point2D.setLocation(point2D.getX(), point2D.getY() - speed);
        g.drawString(String.valueOf(damage), (int) point2D.getX(), (int) point2D.getY());
    }

    public boolean outOfBounds() {
        return !Game.screens().current().getBoundingBox().contains(point2D);
    }
}
