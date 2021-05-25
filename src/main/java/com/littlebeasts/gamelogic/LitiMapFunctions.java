package com.littlebeasts.gamelogic;

import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;

import java.awt.geom.Point2D;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiMapFunctions {

    public static boolean isInProximity(IEntity firstEntity, Point2D point2D) {
        double deltaX = firstEntity.getCenter().getX() - point2D.getX();
        double deltaY = firstEntity.getCenter().getY() - point2D.getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double threshold = 18;
        return distance < threshold;
    }

    public static boolean isFacingInteractable(Creature creature, Point2D targetEntity) {
        int tolerance = 8;
        if (DEBUG_CONSOLE_OUT)
            System.out.println("dY: " + (creature.getCenter().getY() - targetEntity.getY()) + " | dX: " + (creature.getCenter().getX() - targetEntity.getX()));
        switch (creature.getFacingDirection()) {
            case UP:
                return (creature.getCenter().getY() - targetEntity.getY()) > 0 && creature.getCenter().getX() >= targetEntity.getX() - tolerance && creature.getCenter().getX() <= targetEntity.getX() + tolerance;
            case DOWN:
                return (creature.getCenter().getY() - targetEntity.getY()) < 0 && creature.getCenter().getX() >= targetEntity.getX() - tolerance && creature.getCenter().getX() <= targetEntity.getX() + tolerance;
            case LEFT:
                return (creature.getCenter().getX() - targetEntity.getX()) > 0 && creature.getCenter().getY() >= targetEntity.getY() - tolerance && creature.getCenter().getY() <= targetEntity.getY() + tolerance;
            case RIGHT:
                return (creature.getCenter().getX() - targetEntity.getX()) < 0 && creature.getCenter().getY() >= targetEntity.getY() - tolerance && creature.getCenter().getY() <= targetEntity.getY() + tolerance;
            default:
                return false;
        }
    }
}
