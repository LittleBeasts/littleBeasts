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

public class AttackAnimation {

    static boolean soundPlayer = false;
    static IUpdateable forceCheck;
    static AnimationListener animationListener;

    @Action(description = "This is a punch, it hurts.")
    public static void punch(IMobileEntity attacker, IMobileEntity defender) {
        System.out.println("Punch");
        if (!soundPlayer) {
            Game.audio().playSound("punch");
            Game.world().camera().shake(1, 0, 100);
            soundPlayer = true;
        }
        attacker.attachControllers();
        attacker.movement().attach();
        Point2D origin = attacker.getCollisionBoxCenter();
        Point2D target = new Point2D.Double(origin.getX() + 50, origin.getY());
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(false);
        attacker.movement().apply(force);
        System.out.println(origin);
        System.out.println(target);

        forceCheck = () -> {
            if (force.hasEnded()) {
                attackAnimation(attacker, origin, defender);
            }
        };
        Game.loop().attach(forceCheck);
    }

    private static void attackAnimation(IMobileEntity attacker, Point2D origin, IMobileEntity defender) {
        Game.loop().detach(forceCheck);
        animationListener = new AnimationListener() {
            @Override
            public void played(Animation animation) {
            }

            @Override
            public void finished(Animation animation) {
                getBack(attacker, origin);
            }
        };
        Game.world().environment().add(BattleAnimationEntity.instance());
        BattleAnimationEntity.instance().animations().addListener(animationListener);
        BattleAnimationEntity.instance().animations().setDefault(BattleAnimationEntity.instance().animations().get("battleAnimation-scratchAttack"));
        BattleAnimationEntity.instance().setLocation(defender.getLocation().getX() + 10, defender.getLocation().getY() + 10);
        BattleAnimationEntity.instance().animations().attach();
        BattleAnimationEntity.instance().animations().get("battleAnimation-scratchAttack").start();
    }

    private static void getBack(IMobileEntity iMobileEntity, Point2D origin) {
        BattleAnimationEntity.instance().animations().removeListener(animationListener);
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
        } else if (Program.getGameLogic().getState().equals(GameState.INGAME)) {
            BattleAnimationEntity.instance().die();
        }
        Game.loop().detach(forceCheck);
    }
}
