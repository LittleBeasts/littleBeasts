package com.littleBeasts.battleAnimation;

import calculationEngine.entities.CeAttack;
import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationListener;
import de.gurkenlabs.litiengine.physics.Force;

import java.awt.geom.Point2D;
import java.util.Locale;

public class AttackAnimation {

    static boolean soundPlayer = false;
    static IUpdateable forceCheck;
    static AnimationListener animationListener;

    public static void startMeleeAnimation(IMobileEntity attacker, IMobileEntity defender, CeAttack ceAttack) {
        if (!soundPlayer) {
            Game.audio().playSound("punch");
            soundPlayer = true;
        }
        String animationName = "battleAnimation-" + ceAttack.getName().toLowerCase(Locale.ROOT);
        attacker.attachControllers();
        attacker.movement().attach();
        Point2D origin = attacker.getCollisionBoxCenter();
        Point2D target = defender.getCollisionBoxCenter();
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(true);
        attacker.movement().apply(force);

        forceCheck = () -> {
            if (force.hasEnded()) {
                attackAnimation(attacker, origin, defender, animationName);
            }
        };
        Game.loop().attach(forceCheck);
    }

    private static void attackAnimation(IMobileEntity attacker, Point2D origin, IMobileEntity defender, String animationName) {
        Game.loop().detach(forceCheck);
        animationListener = new AnimationListener() {
            @Override
            public void played(Animation animation) {
                Game.world().camera().shake(1, 0, 100);
            }

            @Override
            public void finished(Animation animation) {
                BattleAnimationEntity.instance().setVisible(false);
                returnToOrigin(attacker, origin);
            }
        };
        playAttackAnimation(defender, animationName);
    }

    private static void playAttackAnimation(IMobileEntity animationTarget, String animationName) {
        Game.world().environment().add(BattleAnimationEntity.instance());
        BattleAnimationEntity.instance().animations().addListener(animationListener);
        BattleAnimationEntity.instance().animations().setDefault(BattleAnimationEntity.instance().animations().get(animationName));
        BattleAnimationEntity.instance().setVisible(true);
        BattleAnimationEntity.instance().animations().get(animationName).start();
        BattleAnimationEntity.instance().setLocation(animationTarget.getLocation().getX() + 10, animationTarget.getLocation().getY() + 10);
    }

    private static void returnToOrigin(IMobileEntity iMobileEntity, Point2D origin) {
        BattleAnimationEntity.instance().animations().removeListener(animationListener);
        origin = new Point2D.Double(origin.getX() - 7, origin.getY());
        Force force1 = new Force(origin, 100, 1);
        iMobileEntity.movement().apply(force1);
        forceCheck = () -> {
            if (force1.hasEnded()) {
                resetAnimation();
            }
        };
        Game.loop().attach(forceCheck);
    }

    private static void resetAnimation() {
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
