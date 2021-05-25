package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.entities.MapArea;

import java.awt.geom.Point2D;

public class LitiAreaSign implements Interactable {
    private final String name;
    private final MapArea area;
    private String text, direction;
    private Point2D adjustedCenter;

    public LitiAreaSign(MapArea area) {
        name = area.getName();
        this.area = area;
        direction = name.substring(name.indexOf("-") + 1);
        direction = direction.substring(0, direction.indexOf("-"));
        if (area.getProperties().getProperty("text") != null)
            text = area.getProperties().getProperty("text").getAsString();
        getCorrectCenter();
    }

    private void getCorrectCenter() {
        adjustedCenter = area.getCenter();
        switch (direction) {
            case "NORTH":
                adjustedCenter.setLocation(area.getCenter().getX(), area.getCenter().getY() - 16);
                break;
            case "SOUTH":
                adjustedCenter.setLocation(area.getCenter().getX(), area.getCenter().getY() + 16);
                break;
            case "WEST":
                adjustedCenter.setLocation(area.getCenter().getX() - 16, area.getCenter().getY());
                break;
            case "EAST":
                adjustedCenter.setLocation(area.getCenter().getX() + 16, area.getCenter().getY());
                break;
        }
    }

    @Override
    public void interact() {
        if (text == null) return;
        LitiPlayer.instance().speak(text);
    }

    @Override
    public Point2D getCenter() {
        return adjustedCenter;
    }
}
