package com.littleBeasts.gameLogic;

import calculationEngine.battle.CeBattle;
import calculationEngine.entities.CeAi;
import calculationEngine.entities.CeBeasts;
import calculationEngine.entities.CePlayer;
import com.littleBeasts.entities.LitiBeast;
import com.littleBeasts.entities.LitiPlayer;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Camera;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiBattle {
    private static CeBattle battle;
    private static boolean nextBattlePossible = true;
    private static final List<LitiBeast> LITI_BEAST_LIST = new ArrayList<>(); // list to resolve all animation before removing entity TODO: find a way to finish animation w/o this list.
    private static CePlayer cePlayer;

    public static void triggerBattle() {
        int x = 0;
        boolean faceLeft = false;
        if (LitiPlayer.instance().getFacingDirection() == Direction.LEFT) {
            x = (int) LitiPlayer.instance().getX() - 50;
            faceLeft = true;
        } else {
            x = (int) LitiPlayer.instance().getX() + 50;
        }
        Camera battleCam = new Camera();
        battleCam.setClampToMap(false);
        Point2D point2D = Game.world().camera().getViewportLocation(LitiPlayer.instance());
        Game.world().setCamera(battleCam);
        Game.world().camera().setZoom(1.5f, 500);
        Game.world().camera().setFocus(LitiPlayer.instance().getX() + (faceLeft ? -25 : 25), LitiPlayer.instance().getY());

        //for dev purposes
        LitiBeast litiBeast = new LitiBeast(CeBeasts.FeuerFurz, x, (int) (LitiPlayer.instance().getY() - (LitiPlayer.instance().getHeight() / 2)), false);
        litiBeast.setFacingDirection(LitiPlayer.instance().getFacingDirection().getOpposite());
        LITI_BEAST_LIST.add(litiBeast);

        cePlayer = LitiPlayer.instance().getCePlayer();
        CeAi ai = new CeAi(litiBeast.getCeEntity());
        battle = new CeBattle(LitiPlayer.instance().getCePlayer(), ai);
        LitiPlayer.instance().setBattle(battle);
        LitiPlayer.instance().setIsFighting(true);
    }

    public static void startBattle() {
        if (LitiPlayer.instance().isFighting()) {
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == cePlayer.getNumber()) {

                }
            } else {
                if (DEBUG_CONSOLE_OUT) System.out.println("End of fight");
                GameLogic.setState(GameState.INGAME);
            }
        }
    }

    public static CeBattle getBattle() {
        return battle;
    }

    public static void removeBeast(){
        if (GameLogic.getState() == GameState.INGAME) {
            for (int i = 0; i < LITI_BEAST_LIST.size(); i++) {
                if (LITI_BEAST_LIST.get(i).getBeastStats().isReadyToBeRemoved()) {
                    LITI_BEAST_LIST.get(i).die();
                    Game.world().environment().remove(LITI_BEAST_LIST.get(i));
                    LITI_BEAST_LIST.remove(i);
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

    public static void setNextBattlePossible(boolean nextBattlePossible) {
        LitiBattle.nextBattlePossible = nextBattlePossible;
    }

    public static void update(){
       startBattle();
       removeBeast();
    }
}
