package com.littleBeasts.entities;

import calculationEngine.battle.Battle;
import calculationEngine.entities.*;
import com.littleBeasts.GameLogic;
import com.littleBeasts.PlayerState;
import com.littleBeasts.abilities.Attack;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.Force;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true)
public class Player extends Creature implements IUpdateable, IMobileEntity {
    private static Player playerInstance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<Beast> littleBeastTeam;
    private boolean spawned;
    private Battle battle;
    private GameLogic gameLogic;


    private final CePlayer cePlayer;
    private CeAttack[] playerCeAttacks;
    private String playerName = "xXBeastSlayerXx";
    private int maxHP, currentHP;
    private boolean isFighting;
    private final Image playerPortrait;


    private final Attack punch; // TODO: create correct Attack structure similar to CE

    // TODO: Change add draw prefix to every drawing class
    public Player() {
        super("test");
        this.playerPortrait = Resources.images().get("sprites/char.png");
        // Calculation Engine
        this.littleBeastTeam = new ArrayList<>();
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true)); // ToDo: Change with Teamlogic
        this.addToLittleBeastTeam(new Beast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true));
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        this.cePlayer = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 100, 100, 1, 1, 100, 1, 20, 100, 1, beastsToCeEntities(littleBeastTeam));
        this.playerCeAttacks = cePlayer.getCeEntity().getAttacks();
        this.maxHP = cePlayer.getCeEntity().getMaxHitPoints();
        this.currentHP = cePlayer.getCeEntity().getHitPoints();

        // LITIengine
        this.addController(new KeyboardEntityController<>(this));
        this.punch = new Attack(this);
    }

    public static Player instance() {
        if (playerInstance == null) {
            playerInstance = new Player();
        }
        return playerInstance;
    }

    @Override
    public void update() {
        spawnPlayer();
    }

    // ToDo: Change against "real" spawn logic
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

    public CeAttack[] getPlayerAttacks() {
        return playerCeAttacks;
    }

    public List<Beast> getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public void addToLittleBeastTeam(Beast beast) {
        littleBeastTeam.add(beast);
        int position = littleBeastTeam.indexOf(beast);
        // ToDo: cleanUp Constructor and change Class Name (BeastStats)
        littleBeastTeam.get(position).createBeastStats();
    }

    public void removeFromLittleBeastTeam(Beast beast) {
        littleBeastTeam.remove(beast);
    }

    public void removeFromLittleBeastTeam(int positionOfBeast) {
        littleBeastTeam.remove(littleBeastTeam.get(positionOfBeast));
    }

    public List<CeEntity> beastsToCeEntities(List<Beast> beasts) {
        List<CeEntity> entityList = new ArrayList<>();
        for (Beast beast : beasts) {
            entityList.add(beast.getCeEntity());
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

    public CePlayer getCePlayer() {
        return cePlayer;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void setIsFighting(boolean fighting) {
        isFighting = fighting;
    }

    public boolean isFighting() {
        return isFighting;
    }

    // ToDo: remove pointer with static class
    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    // ToDo: extract to Attack Animation class or similar
    @Action(description = "This is a punch, it hurts.")
    public void punch() {
        this.attachControllers();
        this.movement().attach();
        //Collection<Animation> animations = this.animations().getAll();
        //Animation animation = this.animations().get("test-walk-right");

        Point2D origin = this.getCollisionBoxCenter();
        Point2D target = new Point2D.Double(origin.getX() + 50, origin.getY());
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(true);

        this.movement().apply(force);
        //this.animations().get("test-walk-right").setLooping(true);
        //this.animations().get("test-walk-right").restart();
        if (DEBUG_CONSOLE_OUT) System.out.println(origin.toString());
        if (DEBUG_CONSOLE_OUT) System.out.println(target.toString());
        //this.punch.cast();


        Runnable applyForce = () -> {
            while (!force.hasEnded()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            getBack(origin);
        };
        applyForce.run();

    }

    private void getBack(Point2D origin) {
        origin = new Point2D.Double(origin.getX() - 7, origin.getY());
        Force force1 = new Force(origin, 100, 1);
        this.movement().apply(force1);

        Runnable applyForce = () -> {
            while (!force1.hasEnded()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isFighting) {

                while (!Player.instance().animations().get("test-idle-right").isPlaying()) {
                    Player.instance().animations().get("test-idle-right").start();
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Player.instance().movement().detach();
                Player.instance().detachControllers();
            }
        };
        applyForce.run();

    }

    public Image getPlayerPortrait() {
        return playerPortrait;
    }
}
