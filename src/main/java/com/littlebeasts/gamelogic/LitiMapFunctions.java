package com.littlebeasts.gamelogic;

import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IEntity;

public class LitiMapFunctions {
    public static boolean isInProximity(IEntity firstEntity, IEntity secondEntity) {
        double deltaX = firstEntity.getCenter().getX() - secondEntity.getCenter().getX();
        double deltaY = firstEntity.getCenter().getY() - secondEntity.getCenter().getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double threshold = 18;
        return distance < threshold;
    }

    public static boolean isFacingInteractable(Creature creature, IEntity targetEntity) {
        int tolerance = 8;

        switch (creature.getFacingDirection()) {
            case UP:
                return (creature.getCenter().getY() - targetEntity.getCenter().getY()) > 0 && creature.getCenter().getX() >= targetEntity.getCenter().getX() - tolerance && creature.getCenter().getX() <= targetEntity.getCenter().getX() + tolerance;
            case DOWN:
                return (creature.getCenter().getY() - targetEntity.getCenter().getY()) < 0 && creature.getCenter().getX() >= targetEntity.getCenter().getX() - tolerance && creature.getCenter().getX() <= targetEntity.getCenter().getX() + tolerance;
            case LEFT:
                return (creature.getCenter().getX() - targetEntity.getCenter().getX()) > 0 && creature.getCenter().getY() >= targetEntity.getCenter().getY() - tolerance && creature.getCenter().getY() <= targetEntity.getCenter().getY() + tolerance;
            case RIGHT:
                return (creature.getCenter().getX() - targetEntity.getCenter().getX()) < 0 && creature.getCenter().getY() >= targetEntity.getCenter().getY() - tolerance && creature.getCenter().getY() <= targetEntity.getCenter().getY() + tolerance;
        }
        return false;
    }
}
