package com.littleBeasts.battleAnimation;

import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationListener;
import de.gurkenlabs.litiengine.physics.Force;

import java.awt.geom.Point2D;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class AttackAnimation {

    static boolean soundPlayer = false;
    static IUpdateable forceCheck;

    @Action(description = "This is a punch, it hurts.")
    public static void punch(IMobileEntity iMobileEntity) {
        if (!soundPlayer) {
            Game.audio().playSound("punch");
            Game.world().camera().shake(1, 0, 100);
            soundPlayer = true;
        }
        iMobileEntity.attachControllers();
        iMobileEntity.movement().attach();
        Point2D origin = iMobileEntity.getCollisionBoxCenter();
        Point2D target = new Point2D.Double(origin.getX() + 50, origin.getY());
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(true);
        iMobileEntity.movement().apply(force);
        if (DEBUG_CONSOLE_OUT) System.out.println(origin);
        if (DEBUG_CONSOLE_OUT) System.out.println(target);

        forceCheck = () -> {
            if (force.hasEnded()) {
                attackAnimation(iMobileEntity, origin);
            }
        };
        Game.loop().attach(forceCheck);
    }

    private static void attackAnimation(IMobileEntity iMobileEntity, Point2D origin) {
        Game.loop().detach(forceCheck);
        AnimationListener animationListener = new AnimationListener() {
            @Override
            public void played(Animation animation) {
                //System.out.println("Animation started: " + animation.getName());
            }

            @Override
            public void finished(Animation animation) {
                //System.out.println("Animation done: " + animation.getName());
                getBack(iMobileEntity, origin);
            }
        };

        BattleAnimationEntity.instance().animations().addListener(animationListener);
        BattleAnimationEntity.instance().animations().setDefault(BattleAnimationEntity.instance().animations().get("battleAnimation-scratchAttack"));
        BattleAnimationEntity.instance().setLocation(LitiPlayer.instance().getLocation().getX(), LitiPlayer.instance().getLocation().getY());
        System.out.println(BattleAnimationEntity.instance().animations().get("battleAnimation-scratchAttack").getTotalDuration());
        BattleAnimationEntity.instance().animations().get("battleAnimation-scratchAttack").start();
    }

    private static void getBack(IMobileEntity iMobileEntity, Point2D origin) {
        origin = new Point2D.Double(origin.getX() - 7, origin.getY());
        Force force1 = new Force(origin, 100, 1);
        iMobileEntity.movement().apply(force1);
        forceCheck = () -> {
            if (force1.hasEnded()) {
                getBackContinue();
            }
        };
        Game.loop().attach(forceCheck);
    }

    private static void getBackContinue() {
        if (Program.getGameLogic().getState().equals(GameState.BATTLE)) {
            while (!LitiPlayer.instance().animations().get("test-idle-right").isPlaying()) {
                LitiPlayer.instance().animations().get("test-idle-right").start();
            }
            LitiPlayer.instance().movement().detach();
            LitiPlayer.instance().detachControllers();
        }
        Game.loop().detach(forceCheck);
    }
}
