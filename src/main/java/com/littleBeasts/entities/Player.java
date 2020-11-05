package com.littleBeasts.entities;

import calculationEngine.entities.Attack;
import calculationEngine.entities.Attacks;
import calculationEngine.entities.Beasts;
import com.littleBeasts.PlayerState;
import config.HudConstants;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;

import java.util.ArrayList;
import java.util.List;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 2, collision = true)
@CombatInfo(hitpoints = 100, team = 1)
public class Player extends Creature implements IUpdateable {
    private static Player instance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<Beast> littleBeastTeam;
    private List<Attack> playerAttacks;

    private Player() {
        super("test");
        this.addController(new KeyboardEntityController<>(this));
        this.littleBeastTeam = new ArrayList<>();
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
        this.playerAttacks = new ArrayList<>();
        this.addAttack(new Attack(Attacks.Punch));
        //this.littleBeastTeam.add(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
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

    public void removeController() {
        this.detachControllers();
    }

    public void addController() {
        this.addController(new KeyboardEntityController<>(this));
    }

    public List<Beast> getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public void addToLittleBeastTeam(Beast beast) {
        littleBeastTeam.add(beast);
        int position = littleBeastTeam.indexOf(beast);
        littleBeastTeam.get(position).createBeastStats(HudConstants.TEAM_START_POINT + position * (HudConstants.TILE_GAP + HudConstants.HUD_TILE_WIDTH), HudConstants.HEIGHT - HudConstants.BOTTOM_PAD, HudConstants.HUD_TILE_WIDTH, HudConstants.HUD_ROW_HEIGHT);
    }

    public void addAttack(Attack attack) {
        playerAttacks.add(attack);
    }

    public List<Attack> getAttacks() {
        return playerAttacks;
    }

    public void removeFromLittleBeastTeam(Beast beast) {
        littleBeastTeam.remove(beast);
    }

    public void removeFromLittleBeastTeam(int positionOfBeast) {
        littleBeastTeam.remove(littleBeastTeam.get(positionOfBeast));
    }

}