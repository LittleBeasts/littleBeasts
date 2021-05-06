package com.littleBeasts.entities;

import calculationEngine.battle.CeBattle;
import calculationEngine.entities.*;
import calculationEngine.environment.CeItem;
import com.littleBeasts.Program;
import com.littleBeasts.abilities.Attack;
import com.littleBeasts.battleAnimation.AttackAnimation;
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
public class LitiPlayer extends Creature implements IMobileEntity {
    private static LitiPlayer litiPlayerInstance;
    private static PlayerState state = PlayerState.CONTROLLABLE;
    private List<LitiBeast> littleBeastTeam;
    private boolean spawned;
    private CeBattle battle;
    private GameLogic gameLogic;
    private AttackAnimation attackAnimation;


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
        this.attackAnimation = new AttackAnimation(this);
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

    @Action(description = "Interaction with environment")
    public void interact() {
        if (DEBUG_CONSOLE_OUT) System.out.println("Interaction");
        ArrayList<LitiInteractable> interactables = Program.getGameLogic().getCurrentLitiMap().getInteractables();
        for (LitiInteractable litiInteractable : interactables) {
            if (litiInteractable.isInProximity(LitiPlayer.instance()) && litiInteractable.isFacingInteractable(this)) {

                if (litiInteractable.isNpc()) {
                    litiInteractable.getLitiNPC().getGreeting();
                } else if (!litiInteractable.isNpc()) {
                    litiInteractable.getLitiProp().interact();
                }
            }
        }
    }


    public Image getPlayerPortrait() {
        return playerPortrait;
    }
    public List<CeItem> getPlayerItems() {
        return playerItems;
    }

    public AttackAnimation getAttackAnimation() {
        return attackAnimation;
    }
}
