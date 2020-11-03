package com.littleBeasts.entities;

import com.littleBeasts.PlayerState;

import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 2, collision = true)
@CombatInfo(hitpoints = 100, team = 1)
public class Player extends Creature implements IUpdateable {
    private static Player instance;
    private static PlayerState state = PlayerState.CONTROLLABLE;

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

    public PlayerState getState() {
        return state;
    }

    public void removeController(){
        this.detachControllers();
    }

    public void addController(){
        this.addController(new KeyboardEntityController<>(this));
    }

}