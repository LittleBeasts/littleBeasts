package com.littleBeasts.screens;

import config.HudConstants;
import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.awt.geom.Point2D;

public class DamageAnimation {
    private final Point2D point2D;
    private final int damage, fadeoutSpeed;
    private final double speed;
    private final double animationLimit;
    private int increment;
    private boolean soundPlayer = false;

    public DamageAnimation(Point2D point2D, int damage) {
        this.point2D = point2D;
        this.increment = 0;
        this.damage = damage;
        this.speed = 0.5;
        this.fadeoutSpeed = 2;
        this.animationLimit = point2D.getY() - 40;
    }

    public void draw(Graphics2D g) {
        if (!soundPlayer) { // TODO: These effects need to be moved to the actual attack.
            Game.audio().playSound("punch");
            Game.world().camera().shake(1, 0, 100);
            soundPlayer = true;
        }
        g.setFont(HudConstants.ChatWindowFont);
        g.setColor(new Color(255, 0, 0, (Math.max((int) (255 - (fadeoutSpeed * increment++)), 0))));

        point2D.setLocation(point2D.getX(), point2D.getY() - speed);
        Point2D drawPoint = Game.world().camera().getViewportLocation(point2D);
        drawPoint = new Point2D.Double(drawPoint.getX() * Game.world().camera().getRenderScale(), drawPoint.getY() * Game.world().camera().getRenderScale());
        g.drawString(String.valueOf(damage), (int) drawPoint.getX(), (int) drawPoint.getY());
    }

    public boolean outOfBounds() {
        return point2D.getY() <= animationLimit;
    }
}
