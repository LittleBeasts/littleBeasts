package com.littleBeasts.entities;

import calculationEngine.battle.CeBattle;
import calculationEngine.entities.*;
import calculationEngine.environment.CeItem;
import com.littleBeasts.abilities.Attack;
import com.littleBeasts.gameLogic.GameLogic;
import com.littleBeasts.gameLogic.GameState;
import com.littleBeasts.gameLogic.LitiMap;
import com.littleBeasts.gameLogic.PlayerState;
import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.Force;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 100)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 6, collision = true, align = Align.CENTER, valign = Valign.DOWN, collisionType = Collision.DYNAMIC)
public class LitiPlayer extends Creature implements IUpdateable, IMobileEntity {
    private static LitiPlayer litiPlayerInstance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<LitiBeast> littleBeastTeam;
    private boolean spawned;
    private CeBattle battle;
    private GameLogic gameLogic;


    private final CePlayer cePlayer;
    private List<CeAttack> playerCeAttacks;
    private List<CeItem> playerItems;
    private String playerName = "xXx_BeastSlayer_xXx";
    private int maxHP, currentHP;
    private boolean isFighting;
    private final Image playerPortrait;
    private final Attack punch; // TODO: create correct Attack structure similar to CE

    // TODO: Change add draw prefix to every drawing class
    public LitiPlayer() {
        super("test");
        this.playerPortrait = Resources.images().get("sprites/char.png");
        // Calculation Engine
        this.littleBeastTeam = new ArrayList<>();
        this.addToLittleBeastTeam(new LitiBeast(CeBeasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true)); // ToDo: Change with Teamlogic
        this.addToLittleBeastTeam(new LitiBeast(CeBeasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true));
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        List<CeAttack> attacks = new ArrayList<>();
        attacks.add(new CeAttack(CeAttacks.Punch));
        attacks.add(new CeAttack(CeAttacks.Flee));
        this.cePlayer = new CePlayer(new CeStats(CeBeastTypes.PlayerStandard, CeNature.ANGRY, 1, 100, 100, 20, 1, 20, 10, 1), attacks, beastsToCeEntities(littleBeastTeam), false);
        this.playerCeAttacks = cePlayer.getAttacks();
        this.maxHP = cePlayer.getCeStats().getMaxHitPoints();
        this.currentHP = cePlayer.getCeStats().getCurrentHitPoints();

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

    }

    public PlayerState getState() {
        return state;
    }

    public List<CeAttack> getPlayerAttacks() {
        return playerCeAttacks;
    }

    public List<LitiBeast> getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public void addToLittleBeastTeam(LitiBeast litiBeast) {
        littleBeastTeam.add(litiBeast);
        int position = littleBeastTeam.indexOf(litiBeast);
        // ToDo: cleanUp Constructor and change Class Name (BeastStats)
        littleBeastTeam.get(position).createBeastStats();
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

    public CeBattle getBattle() {
        return battle;
    }

    public void setBattle(CeBattle battle) {
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


    @Action(description = "Interaction with environment")
    public void interact() {
        if (DEBUG_CONSOLE_OUT) System.out.println("Interaction");
        ArrayList<LitiInteractable> interactables = LitiMap.getInteractables();
        for (LitiInteractable litiInteractable : interactables) {
            if (litiInteractable.isInProximity(LitiPlayer.instance()) && this.isFacingPoint(litiInteractable.getiEntity().getCenter())) {

                if (litiInteractable.isNpc()) {
                    litiInteractable.getLitiNPC().getGreeting();
                } else if (!litiInteractable.isNpc()) {
                    litiInteractable.getLitiProp().interact();
                }
            }
        }
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

    public boolean isFacingPoint(Point2D point) {
        int tolerance = 8;
        switch (this.getFacingDirection()) {
            case UP:
                return (this.getCenter().getY() - point.getY()) > 0 && this.getCenter().getX() >= point.getX() - tolerance && this.getCenter().getX() <= point.getX() + tolerance;
            case DOWN:
                return (this.getCenter().getY() - point.getY()) < 0 && this.getCenter().getX() >= point.getX() - tolerance && this.getCenter().getX() <= point.getX() + tolerance;
            case LEFT:
                return (this.getCenter().getX() - point.getX()) > 0 && this.getCenter().getY() >= point.getY() - tolerance && this.getCenter().getY() <= point.getY() + tolerance;
            case RIGHT:
                return (this.getCenter().getX() - point.getX()) < 0 && this.getCenter().getY() >= point.getY() - tolerance && this.getCenter().getY() <= point.getY() + tolerance;
        }
        return false;
    }

    public Image getPlayerPortrait() {
        return playerPortrait;
    }
    public List<CeItem> getPlayerItems() {
        return playerItems;
    }
}
