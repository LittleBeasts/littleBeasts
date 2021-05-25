package com.littlebeasts.entities;

import calculationEngine.entities.*;
import com.littlebeasts.Program;
import com.littlebeasts.gameLogic.LitiMapFunctions;
import com.littlebeasts.gameLogic.PlayerState;
import config.FontConstants;
import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
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
    private final Image playerPortrait;
    private final LitiBeastTeam littleBeastTeam;
    private final CeInventory ceInventory;
    private boolean hasController = false;

    public LitiPlayer() {
        super("test");
        this.playerPortrait = Resources.images().get("sprites/char.png");
        this.ceInventory = new CeInventory();
        this.littleBeastTeam = new LitiBeastTeam();
        this.littleBeastTeam.addBeast(new LitiBeast(CeBeasts.FeuerFurz, (int) this.getX(), (int) this.getY(), true));
        // ToDo: Change with new saveGame logic and initialize a new Player correctly
        List<CeAttack> attacks = new ArrayList<>();
        attacks.add(new CeAttack(CeAttacks.Punch));
        attacks.add(new CeAttack(CeAttacks.Flee));
        this.cePlayer = new CePlayer(new CeStats(CeBeastTypes.PlayerStandard, CeNature.ANGRY, 1, 100, 100, 20, 1, 20, 10, 1), attacks, littleBeastTeam.beastsToCeEntities(littleBeastTeam.getBeasts()), false);
    }

    public static LitiPlayer instance() {
        if (litiPlayerInstance == null) {
            litiPlayerInstance = new LitiPlayer();
        }
        return litiPlayerInstance;
    }

    @Action(description = "Interaction with environment")
    public void interact() {
        if (DEBUG_CONSOLE_OUT) System.out.println("Interaction");
        ArrayList<Interactable> interactables = Program.getGameLogic().getCurrentLitiMap().getInteractables();
        for (Interactable interactable : interactables) {
            if (LitiMapFunctions.isInProximity(LitiPlayer.instance(), interactable.getCenter()) && LitiMapFunctions.isFacingInteractable(this, interactable.getCenter())) {
                interactable.interact();
            }
        }
    }

    public void speak(String string) {
        SpeechBubble.create(this, string, SpeechBubble.DEFAULT_APPEARANCE, FontConstants.DEFAULT_FONT);
    }

    public void setState(PlayerState state) {
        playerState = state;
        if (playerState == PlayerState.CONTROLLABLE) {
            if (!hasController) {
                this.addController(new KeyboardEntityController<>(this));
                hasController = true;
            }
            this.attachControllers();
        } else if (playerState == PlayerState.LOCKED) {
            this.detachControllers();
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
        return "xXx_BeastSlayer_xXx";
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

    @Override
    public String toString() {
        return "LitiPlayer{" +
                "cePlayer=" + cePlayer +
                ", playerPortrait=" + playerPortrait +
                ", littleBeastTeam=" + littleBeastTeam +
                ", ceInventory=" + ceInventory +
                ", hasController=" + hasController +
                '}';
    }
}
