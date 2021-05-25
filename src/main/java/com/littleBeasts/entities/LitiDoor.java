package com.littleBeasts.entities;

import config.FontConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.gui.SpeechBubble;

public class LitiDoor implements Interactable {
    LitiProp.PropState propState;
    private final String name;
    private final IEntity iEntity;
    private final Prop prop;
    private boolean isLocked;


    public LitiDoor(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.prop = Game.world().environment().getProp(this.name);
        this.propState = LitiProp.PropState.CLOSED;
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
        propState = LitiProp.PropState.OPENED;
        Game.world().environment().remove(this.iEntity);
    }

    private boolean hasKey() {
        return false;
    }

    @Override
    public IEntity getiEntity() {
        return iEntity;
    }

    @Override
    public String toString() {
        return "LitiProp{" +
                "propState=" + propState +
                ", name='" + name + '\'' +
                '}';
    }
}
