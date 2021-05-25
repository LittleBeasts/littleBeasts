package com.littlebeasts.gameLogic;

import calculationEngine.battle.CeBattle;
import calculationEngine.entities.CeAi;
import calculationEngine.entities.CeBeasts;
import calculationEngine.entities.CePlayer;
import com.littlebeasts.Program;
import com.littlebeasts.battleAnimation.BattleAnimations;
import com.littlebeasts.entities.LitiBeast;
import com.littlebeasts.entities.LitiPlayer;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Camera;

import java.util.ArrayList;
import java.util.List;

public class LitiBattle {
    private static CeBattle ceBattle;
    private static boolean nextBattlePossible = true;
    private static final List<LitiBeast> LITI_BEAST_LIST = new ArrayList<>(); // list to resolve all animation before removing entity TODO: find a way to finish animation w/o this list.
    private static CePlayer cePlayer;
    private static LitiBeast litiBeast;

    public static void triggerBattle() {

        int x;
        boolean faceLeft = false;
        if (LitiPlayer.instance().getFacingDirection() == Direction.LEFT) {
            x = (int) LitiPlayer.instance().getX() - 50;
            faceLeft = true;
        } else {
            x = (int) LitiPlayer.instance().getX() + 50;
        }
        Camera battleCam = new Camera();
        battleCam.setClampToMap(false);
        Game.world().setCamera(battleCam);
        Game.world().camera().setZoom(1.5f, 500);
        Game.world().camera().setFocus(LitiPlayer.instance().getX() + (faceLeft ? -25 : 25), LitiPlayer.instance().getY());

        //for dev purposes
        litiBeast = new LitiBeast(CeBeasts.FeuerFurz, x, (int) (LitiPlayer.instance().getY() - (LitiPlayer.instance().getHeight() / 2)), false);
        litiBeast.setFacingDirection(LitiPlayer.instance().getFacingDirection().getOpposite());
        LITI_BEAST_LIST.add(litiBeast);

        cePlayer = LitiPlayer.instance().getCePlayer();
        CeAi ai = new CeAi(litiBeast.getCeEntity());
        ceBattle = new CeBattle(LitiPlayer.instance().getCePlayer(), ai);
    }

    public static void startBattle() {
        if (ceBattle == null) return;
        if (Program.getGameLogic().getState() == GameState.BATTLE) {
            if (ceBattle.getTurn() != null) {
                if (ceBattle.getTurn().getNumber() == cePlayer.getNumber()) {

                }
            } else {

                Program.getGameLogic().setState(GameState.INGAME);
            }
        }
    }

    public static void createDamageAnimations() {
        if (litiBeast != null) {
            for (int damage : litiBeast.getCeEntity().getDamages())
                BattleAnimations.animateDamage(litiBeast, damage);
        }
        for (LitiBeast litiBeast : LitiPlayer.instance().getLittleBeastTeam().getBeasts()) {
            for (int damage : litiBeast.getCeEntity().getDamages()) {
                BattleAnimations.animateDamage(LitiPlayer.instance(), damage);
            }
        }
        for (int damage : LitiPlayer.instance().getCePlayer().getDamages()) {
            BattleAnimations.animateDamage(LitiPlayer.instance(), damage);
        }
    }

    public static CeBattle getCeBattle() {
        return ceBattle;
    }

    public static void removeBeast() {
        if (Program.getGameLogic().getState() == GameState.INGAME) {
            for (int i = 0; i < LITI_BEAST_LIST.size(); i++) {
                if (BattleAnimations.allAnimationsDone()) {
                    LITI_BEAST_LIST.get(i).die();
                    Game.world().environment().remove(LITI_BEAST_LIST.get(i));
                    LITI_BEAST_LIST.remove(LITI_BEAST_LIST.get(i));
                }
                if (LITI_BEAST_LIST.size() == 0) {
                    nextBattlePossible = true;
                }
            }
        }
    }

    public static List<LitiBeast> getBeastList() {
        return LITI_BEAST_LIST;
    }

    public static boolean isNextBattlePossible() {
        return nextBattlePossible;
    }

    public static LitiBeast getLitiBeast() {
        return litiBeast;
    }

    public static void setNextBattlePossible(boolean nextBattlePossible) {
        LitiBattle.nextBattlePossible = nextBattlePossible;
    }

    public static void update() {
        startBattle();
        removeBeast();
        if (Program.getGameLogic().getState() == GameState.BATTLE)
            createDamageAnimations();
    }
}
