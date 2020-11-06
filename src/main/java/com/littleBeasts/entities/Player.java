package com.littleBeasts.entities;

import calculationEngine.entities.*;
import com.littleBeasts.PlayerState;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;

import java.util.ArrayList;
import java.util.List;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true)
@CombatInfo(hitpoints = 100, team = 1)
public class Player extends Creature implements IUpdateable {
    private static Player instance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<Beast> littleBeastTeam;
    private boolean spawned;
    private CePlayer cePlayer;
    private Attack[] playerAttacks;
    private String playerName = "Horst";
    private int maxHP, currentHP;

    public Player() {
        super("test");

        // Calculation Engine
        this.littleBeastTeam = new ArrayList<>();
<<<<<<< HEAD
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
        this.playerAttacks = new ArrayList<>(); // TODO: get CE_Player attacks
        this.addAttack(new Attack(Attacks.Punch));
        //this.littleBeastTeam.add(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY()));
        // setup the player's abilities
=======
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), this.getFacingDirection().getOpposite()));
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), this.getFacingDirection().getOpposite()));
        this.cePlayer = new CePlayer(Nature.ANGRY, new Attack[]{new Attack(Attacks.Punch)}, 1, 1, 1, 1, 1, 1, 1, 1, 1, beastToCeEntity(littleBeastTeam));
        this.playerAttacks = cePlayer.getAttacks();

        this.maxHP = cePlayer.getMaxHitPoints();
        this.currentHP = cePlayer.getHitPoints();

        // LITIengine
        this.addController(new KeyboardEntityController<>(this));

>>>>>>> 3e4bf2d7385d83c47db911e1c64cb93ff192dfde
    }

    public static Player instance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    @Override
    public void update() {
        spawnPlayer();
    }

    public void spawnPlayer() {
        if (!spawned) {
            Spawnpoint spawnpoint = Game.world().environment().getSpawnpoint("west");
            spawnpoint.spawn(this);
            spawned = true;
            this.detachControllers();
        }
    }

    public PlayerState getState() {
        return state;
    }

    public Attack[] getPlayerAttacks() {
        return playerAttacks;
    }

    public List<Beast> getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public void addToLittleBeastTeam(Beast beast) {
        littleBeastTeam.add(beast);
        int position = littleBeastTeam.indexOf(beast);
        littleBeastTeam.get(position).createBeastStats(HudConstants.TEAM_START_POINT + position * (HudConstants.TILE_GAP + HudConstants.HUD_TILE_WIDTH), HudConstants.HEIGHT - HudConstants.BOTTOM_PAD, HudConstants.HUD_TILE_WIDTH, HudConstants.HUD_ROW_HEIGHT);
    }

    public void removeFromLittleBeastTeam(Beast beast) {
        littleBeastTeam.remove(beast);
    }

    public void removeFromLittleBeastTeam(int positionOfBeast) {
        littleBeastTeam.remove(littleBeastTeam.get(positionOfBeast));
    }

    public List<CeEntity> beastToCeEntity(List<Beast> beasts) {
        List<CeEntity> entityList = new ArrayList<>();
        for (Beast beast : beasts) {
            entityList.add(beast.getLittleBeast());
        }
        return entityList;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getCurrentHP() {
        return currentHP;
    }
}