package com.littlebeasts.entities;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeAttacks;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.physics.MovementController;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 110)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = false)
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
        followPlayer();
    }

    private void followPlayer() {
        float threshold;
        Point2D playerPosition = LitiPlayer.instance().getCenter();
        threshold = this.isIdle() ? 40f : 25f;
        double differenceX = playerPosition.getX() - this.getCenter().getX();
        double differenceY = playerPosition.getY() - this.getCenter().getY();
        double euclideanDistance = Math.sqrt(Math.abs(differenceX * differenceX) + Math.abs(differenceY * differenceY));
        if (euclideanDistance > threshold) {
            if (Math.abs(differenceX) > threshold / 2)
                this.movementController.setDx((float) differenceX);
            if (Math.abs(differenceY) > threshold / 2)
                this.movementController.setDy((float) differenceY);
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

