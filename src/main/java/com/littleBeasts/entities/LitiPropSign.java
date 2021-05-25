package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;

import java.awt.geom.Point2D;

public class LitiPropSign implements Interactable {

    private final String name;
    private final IEntity iEntity;
    private final Prop prop;
    private boolean isAccessible;
    private String text;


    public LitiPropSign(IEntity iEntity) {
        name = iEntity.getName();
        this.iEntity = iEntity;
        prop = Game.world().environment().getProp(this.name);
        isAccessible = true;
        if (prop.getProperties().getProperty("prerequisite") != null)
            this.isAccessible = prop.getProperties().getProperty("prerequisite").getAsBool();
        if (prop.getProperties().getProperty("text") != null)
            text = prop.getProperties().getProperty("text").getAsString();
    }

    @Override
    public void interact() {
        if (!isAccessible || text == null) return;
        LitiPlayer.instance().speak(text);
    }

    @Override
    public Point2D getCenter() {
        return iEntity.getCenter();
    }

}
