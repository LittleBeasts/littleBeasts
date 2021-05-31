package com.littlebeasts.battleanimation;

import config.HudConstants;
import de.gurkenlabs.litiengine.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class DamageAnimation {
    private final Point2D point2D;
    private final int damage;
    private final int fadeoutSpeed;
    private final double speed;
    private final double animationLimit;
    private int increment;

    public DamageAnimation(Point2D point2D, int damage) {
        this.point2D = point2D;
        this.increment = 0;
        this.damage = damage;
        this.speed = 0.5;
        this.fadeoutSpeed = 2;
        this.animationLimit = point2D.getY() - 40;
    }

    public void draw(Graphics2D g) {
        g.setFont(HudConstants.ChatWindowFont);
        // ToDo: Extract to HudConstants
        g.setColor(new Color(255, 0, 0, (Math.max(255 - (fadeoutSpeed * increment++), 0))));

        // ToDo: Add vertical wiggle in draw damage Animation
        point2D.setLocation(point2D.getX(), point2D.getY() - speed);
        Point2D drawPoint = Game.world().camera().getViewportLocation(point2D);
        drawPoint = new Point2D.Double(drawPoint.getX() * Game.world().camera().getRenderScale(), drawPoint.getY() * Game.world().camera().getRenderScale());
        g.drawString(String.valueOf(damage), (int) drawPoint.getX(), (int) drawPoint.getY());
    }

    public boolean outOfBounds() {
        return point2D.getY() <= animationLimit;
    }
}
