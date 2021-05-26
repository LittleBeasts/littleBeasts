package com.littlebeasts.entities;

import java.awt.geom.Point2D;

public interface Interactable {

    void interact();

    Point2D getCenter();
}