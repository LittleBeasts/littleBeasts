package com.littleBeasts.movement;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.entities.behavior.AStarGrid;
import de.gurkenlabs.litiengine.entities.behavior.AStarPathFinder;
import de.gurkenlabs.litiengine.entities.behavior.Path;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class LitiPathfindingController {
    private static AStarPathFinder currentPathFinder;
    private static AStarGrid currentGrid;
    private static String mapName = "";

    public static Path getPath(IMobileEntity entity, Point2D target) {
        if (!mapName.equals(Game.world().environment().getMap().getName())) {
            mapName = Game.world().environment().getMap().getName();
            currentPathFinder = new AStarPathFinder(Game.world().environment().getMap());
            currentGrid = currentPathFinder.getGrid();
        }
        return currentPathFinder.findPath(entity, target);
    }


}
