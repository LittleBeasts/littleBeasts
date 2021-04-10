package com.littleBeasts.entities;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeAttacks;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.physics.MovementController;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true)
public class LitiPet extends Creature implements IUpdateable, IMobileEntity {
    private static LitiPet litiPetInstance;
    private boolean spawned;

    private List<CeAttack> petCeAttacks;
    private String petName = "xXx_Beast_xXx";
    private MovementController movementController;
    private int maxHP, currentHP;
    //private final Image playerPortrait;


    //private final Attack punch; // TODO: create correct Attack structure similar to CE

    // TODO: Change add draw prefix to every drawing class
    public LitiPet() {
        super("bwcat");
        //this.playerPortrait = Resources.images().get("sprites/char.png");
        // Calculation Engine
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        List<CeAttack> attacks = new ArrayList<>();
        attacks.add(new CeAttack(CeAttacks.Punch));
        // LITIengine
        //this.addController(new KeyboardEntityController<>(this));
        movementController = new MovementController<>(this);
        this.addController(movementController);
    }

    public static LitiPet instance() {
        if (litiPetInstance == null) {
            litiPetInstance = new LitiPet();
        }
        return litiPetInstance;
    }

    @Override
    public void update() {
        spawnPet();
        Float threshold;
        Point2D playerPosition = LitiPlayer.instance().getCenter();
        //Path petPath;
        //List<Point2D> pathPoints = null;
        //if (pathPoints == null || pathPoints.isEmpty()) {
        //    pathPoints = LitiPathfindingController.getPath(this, playerPosition).getPoints();
        //}
        threshold = this.isIdle() ? 35f : 15f;
        //Point2D pathPoint = pathPoints.remove(0);
        //double x1 = this.getCenter().getX();
        //double x2 = pathPoint.getX();
        //double y1 = this.getCenter().getY();
        //double y2 = pathPoint.getY();
        double differenceX = playerPosition.getX() - this.getCenter().getX();
        double differenceY = playerPosition.getY() - this.getCenter().getY();
        double euclideanDistance = Math.sqrt(Math.abs(differenceX * differenceX) + Math.abs(differenceY * differenceY));
        if (euclideanDistance > threshold ) {
            this.movementController.setDx((float) differenceX);
            this.movementController.setDy((float) differenceY);
        }
    }
    //this.movementController.update();


    public void spawnPet() {
        if (!spawned) {
            // ToDo: Should be at the player
            Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint("west");
            spawnpoint.spawn(this);
            spawned = true;
            this.detachControllers();
        }
    }

    public List<CeAttack> getPetAttacks() {
        return petCeAttacks;
    }

    public String getPetName() {
        return petName;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }

}

