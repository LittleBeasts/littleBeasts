package com.littleBeasts.entities;

import calculationEngine.entities.*;
import calculationEngine.environment.CeItem;
import com.littleBeasts.Program;
import com.littleBeasts.gameLogic.LitiMapFunctions;
import com.littleBeasts.gameLogic.PlayerState;
import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 100)
@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 6, collision = true, align = Align.CENTER, valign = Valign.DOWN, collisionType = Collision.DYNAMIC)
public class LitiPlayer extends Creature implements IMobileEntity {
    private static LitiPlayer litiPlayerInstance;
    private static PlayerState playerState = PlayerState.LOCKED;
    private final CePlayer cePlayer;
    private final String playerName = "xXx_BeastSlayer_xXx";
    private final Image playerPortrait;
    private final LitiBeastTeam littleBeastTeam;
    private final CeInventory ceInventory;

    // TODO: Change add draw prefix to every drawing class
    public LitiPlayer() {
        super("test");
        this.playerPortrait = Resources.images().get("sprites/char.png");
        this.ceInventory = new CeInventory();
        // Calculation Engine
        this.littleBeastTeam = new LitiBeastTeam();
        this.littleBeastTeam.addBeast(new LitiBeast(CeBeasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true));
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        List<CeAttack> attacks = new ArrayList<>();
        attacks.add(new CeAttack(CeAttacks.Punch));
        attacks.add(new CeAttack(CeAttacks.Flee));
        this.cePlayer = new CePlayer(new CeStats(CeBeastTypes.PlayerStandard, CeNature.ANGRY, 1, 100, 100, 20, 1, 20, 10, 1), attacks, littleBeastTeam.beastsToCeEntities(littleBeastTeam.getBeasts()), false);
        this.addController(new KeyboardEntityController<>(this));
    }

    public static LitiPlayer instance() {
        if (litiPlayerInstance == null) {
            litiPlayerInstance = new LitiPlayer();
        }
        return litiPlayerInstance;
    }

    public void addItemToPlayerInventory() throws NoPlaceInInventoryException {
        CeItem ceItem = new CeItem();
        int[] itemBonusStats = {20, 0, 0, 0, 0, 0, 0};
        ceItem.setNewLootedItem("Test", "Test", false, "consumable", 1, itemBonusStats, "Test");
        this.ceInventory.addItemToInventory(ceItem);
    }

    @Action(description = "Interaction with environment")
    public void interact() {
        if (DEBUG_CONSOLE_OUT) System.out.println("Interaction");
        ArrayList<Interactable> interactables = Program.getGameLogic().getCurrentLitiMap().getInteractables();
        for (Interactable interactable : interactables) {
            if (LitiMapFunctions.isInProximity(LitiPlayer.instance(), interactable.getiEntity()) && LitiMapFunctions.isFacingInteractable(this, interactable.getiEntity())) {
                interactable.interact();
            }
        }
    }

    public void setState(PlayerState state) {
        playerState = state;
        if (playerState == PlayerState.CONTROLLABLE) {
            LitiPlayer.instance().attachControllers();
            LitiPlayer.instance().movement().attach();
        } else if (playerState == PlayerState.LOCKED) {
            LitiPlayer.instance().detachControllers();
        }
    }

    public Image getPlayerPortrait() {
        return playerPortrait;
    }

    public LitiBeastTeam getLittleBeastTeam() {
        return littleBeastTeam;
    }

    public PlayerState getState() {
        return playerState;
    }

    public List<CeAttack> getPlayerAttacks() {
        return this.cePlayer.getAttacks();
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getMaxHP() {
        return this.cePlayer.getCeStats().getMaxHitPoints();
    }

    public CePlayer getCePlayer() {
        return cePlayer;
    }

    public CeInventory getCeInventory() {
        return ceInventory;
    }
}
