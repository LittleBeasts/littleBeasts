package com.littleBeasts.entities;

import config.FontConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.gui.SpeechBubble;

import java.awt.geom.Point2D;

public class LitiPropDoor implements Interactable {
    private PropState propState;
    private final String name;
    private final IEntity iEntity;
    private final Prop prop;
    private boolean isLocked;

    public LitiPropDoor(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.prop = Game.world().environment().getProp(this.name);
        this.propState = PropState.CLOSED;
        this.isLocked = false;
        if (prop.getProperties().getProperty("locked") != null)
            this.isLocked = prop.getProperties().getProperty("locked").getAsBool();
    }

    @Override
    public void interact() {
        //TODO:Check for Key in Inventory
        System.out.println("Door interactions.");
        if (isLocked && !hasKey()) {
            SpeechBubble.create(LitiPlayer.instance(), "The door is locked.", SpeechBubble.DEFAULT_APPEARANCE, FontConstants.DEFAULT_FONT);
            return;
        }

        this.prop.setVisible(false);
        this.prop.setCollision(false);
        propState = PropState.OPENED;
        Game.world().environment().remove(this.iEntity);
    }

    @Override
    public Point2D getCenter() {
        return iEntity.getCenter();
    }

    private boolean hasKey() {
        return false;
    }

    @Override
    public String toString() {
        return "LitiProp{" +
                "propState=" + propState +
                ", name='" + name + '\'' +
                '}';
    }
}
