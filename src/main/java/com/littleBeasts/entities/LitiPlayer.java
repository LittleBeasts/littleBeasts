package com.littleBeasts.entities;

import calculationEngine.battle.Battle;
import calculationEngine.entities.*;
import com.littleBeasts.GameLogic;
import com.littleBeasts.PlayerState;
import com.littleBeasts.abilities.Attack;
import config.HudConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.Force;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 14, collision = true)
public class LitiPlayer extends Creature implements IUpdateable, IMobileEntity {
    private static LitiPlayer litiPlayerInstance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<LitiBeast> littleBeastTeam;
    private boolean spawned;
    private Battle battle;
    private GameLogic gameLogic;


    private final CePlayer cePlayer;
    private CeAttack[] playerCeAttacks;
    private String playerName = "Horst";
    private int maxHP, currentHP;
    private boolean isFighting;


    private final Attack punch; // TODO: create correct Attack structure similar to CE

    // TODO: Change add draw prefix to every drawing class
    public LitiPlayer() {
        super("test");

        // Calculation Engine
        this.littleBeastTeam = new ArrayList<>();
        this.addToLittleBeastTeam(new LitiBeast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true)); // ToDo: Change with Teamlogic
        this.addToLittleBeastTeam(new LitiBeast(Beasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true));
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        this.cePlayer = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 100, 100, 1, 1, 100, 1, 20, 100, 1, beastsToCeEntities(littleBeastTeam));
        this.playerCeAttacks = cePlayer.getCeEntity().getAttacks();
        this.maxHP = cePlayer.getCeEntity().getMaxHitPoints();
        this.currentHP = cePlayer.getCeEntity().getHitPoints();

        // LITIengine
        this.addController(new KeyboardEntityController<>(this));
        this.punch = new Attack(this);
    }

    public static LitiPlayer instance() {
        if (litiPlayerInstance == null) {
            litiPlayerInstance = new LitiPlayer();
        }
        return litiPlayerInstance;
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

    public List<LitiBeast> getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public void addToLittleBeastTeam(LitiBeast litiBeast) {
        littleBeastTeam.add(litiBeast);
        int position = littleBeastTeam.indexOf(litiBeast);
        // ToDo: cleanUp Constructor and change Class Name (BeastStats)
        int x = HudConstants.TEAM_START_POINT + position * (HudConstants.TILE_GAP + HudConstants.HUD_TILE_WIDTH);
        int y = HudConstants.HEIGHT - HudConstants.BOTTOM_PAD;
        int width = HudConstants.HUD_TILE_WIDTH;
        int height = HudConstants.HUD_ROW_HEIGHT;

        littleBeastTeam.get(position).createBeastStats(x, y, width, height);
    }

    public void removeFromLittleBeastTeam(LitiBeast litiBeast) {
        littleBeastTeam.remove(litiBeast);
    }

    public void removeFromLittleBeastTeam(int positionOfBeast) {
        littleBeastTeam.remove(littleBeastTeam.get(positionOfBeast));
    }

    public List<CeEntity> beastsToCeEntities(List<LitiBeast> litiBeasts) {
        List<CeEntity> entityList = new ArrayList<>();
        for (LitiBeast litiBeast : litiBeasts) {
            entityList.add(litiBeast.getCeEntity());
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
        System.out.println(origin.toString());
        System.out.println(target.toString());
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

                while (!LitiPlayer.instance().animations().get("test-idle-right").isPlaying()) {
                    LitiPlayer.instance().animations().get("test-idle-right").start();
                }
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LitiPlayer.instance().movement().detach();
                LitiPlayer.instance().detachControllers();
            }
        };
        applyForce.run();

    }


}
