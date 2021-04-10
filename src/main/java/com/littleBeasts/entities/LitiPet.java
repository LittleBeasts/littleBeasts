package com.littleBeasts.entities;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeAttacks;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.MovementController;

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

        this.addController(new MovementController<>(this));
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
    }

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
