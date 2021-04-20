package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.entities.IEntity;

public class LitiInteractable {
    private LitiNPC litiNPC;
    private LitiProp litiProp;
    private final boolean isNpc;
    private final IEntity iEntity;

    public LitiInteractable(IEntity iEntity) {
        this.iEntity = iEntity;
        this.litiNPC = new LitiNPC(iEntity);
        this.isNpc = true;
    }

    public LitiInteractable(IEntity iEntity, LitiProp litiProp) {
        this.iEntity = iEntity;
        this.litiProp = litiProp;
        this.isNpc = false;
    }

    public LitiNPC getLitiNPC() {
        return litiNPC;
    }

    public LitiProp getLitiProps() {
        return litiProp;
    }

    public boolean isInProximity(IEntity iEntity) {
        double deltaX = this.iEntity.getCenter().getX() - iEntity.getCenter().getX();
        double deltaY = this.iEntity.getCenter().getY() - iEntity.getCenter().getY();
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double threshold = 18;
        return distance < threshold;
    }

    public IEntity getiEntity() {
        return iEntity;
    }

    @Override
    public String toString() {
        return "LitiInteractable{" +
                ", litiNPC=" + litiNPC.toString() +
                ", litiProp=" + litiProp.toString() +
                ", isNpc=" + isNpc +
                ", iEntity=" + iEntity.getName() +
                '}';
    }

    public boolean isNpc() {
        return isNpc;
    }
}
