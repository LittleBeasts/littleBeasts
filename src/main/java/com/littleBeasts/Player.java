package com.littleBeasts;

import java.awt.geom.Rectangle2D;

import com.littleBeasts.abilities.Jump;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.physics.MovementController;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 2, collision = true)
public class Player extends Creature implements IUpdateable {
    private static Player instance;

    private Player() {
        super("test");


        this.addController(new KeyboardEntityController<>(this));
        // setup the player's abilities

    }

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    @Override
    public void update() {
    }

   // @Override
   // protected IMovementController createMovementController() {
   //     // setup movement controller
   //     return new KeyboardEntityController<>(this);
   // }

}