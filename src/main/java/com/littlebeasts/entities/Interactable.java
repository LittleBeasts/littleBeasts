package com.littlebeasts.entities;

import de.gurkenlabs.litiengine.entities.IEntity;

import java.awt.geom.Point2D;

public interface Interactable {

    void interact();

    Point2D getCenter();
}
