package com.littlebeasts.battleAnimation;

import calculationEngine.entities.CeAttack;
import com.littlebeasts.Program;
import com.littlebeasts.entities.LitiPlayer;
import com.littlebeasts.gameLogic.GameState;
import com.littlebeasts.gameLogic.PlayerState;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationListener;
import de.gurkenlabs.litiengine.physics.Force;

import java.awt.geom.Point2D;
import java.util.Locale;

public class AttackAnimation {

    static IUpdateable forceCheck;
    static AnimationListener animationListener;

    public static void startMeleeAnimation(IMobileEntity attacker, IMobileEntity defender, CeAttack ceAttack) {
        LitiPlayer.instance().setState(PlayerState.LOCKED);
        Game.audio().playSound("punch");
        String animationName = "battleAnimation-" + ceAttack.getName().toLowerCase(Locale.ROOT);
        // attach controller, so the force can be applied (perhaps there is a better way to that)
        attacker.attachControllers();
        attacker.movement().attach();
        Point2D origin = attacker.getCollisionBoxCenter();
        Point2D target = defender.getCollisionBoxCenter();
        // create a force, which pull the entity towards the target
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(true);
        attacker.movement().apply(force);

        forceCheck = () -> {
            if (force.hasEnded()) {
                // trigger the attack animation, when the character has arrived at target.
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
                BattleAnimationEntity.instance().animations().removeListener(this);
                // trigger return animation, when the attack animation is done.
                returnToOrigin(attacker, origin);
            }
        };
        playAttackAnimation(defender, animationName);
    }

    private static void playAttackAnimation(IMobileEntity animationTarget, String animationName) {
        Game.world().environment().add(BattleAnimationEntity.instance());
        BattleAnimationEntity.instance().setVisible(true);
        // add listener to animation so it can trigger when stopped.
        BattleAnimationEntity.instance().animations().addListener(animationListener);
        BattleAnimationEntity.instance().animations().setDefault(BattleAnimationEntity.instance().animations().get(animationName));
        BattleAnimationEntity.instance().setLocation(animationTarget.getLocation().getX() + 10, animationTarget.getLocation().getY() + 10);
        BattleAnimationEntity.instance().animations().get(animationName).start();
    }

    private static void returnToOrigin(IMobileEntity iMobileEntity, Point2D origin) {
        // remove listener, because from now on it will always be finished.

        origin = new Point2D.Double(origin.getX() - 7, origin.getY());
        Force force = new Force(origin, 100, 1);
        iMobileEntity.movement().apply(force);
        ForceListener forceListener = new ForceListener() {
            Force force;
            boolean attached = false;

            @Override
            public void update() {
                this.finished(force);
            }

            @Override
            public void finished(Force force) {
                if (!attached) {
                    Game.loop().attach(this);
                    attached = true;
                }
                this.force = force;
                if (force.hasEnded()) {
                    Game.loop().detach(this);
                    resetAnimation();
                }
            }
        };
        forceListener.finished(force);
    }

    private static void resetAnimation() {
        if (Program.getGameLogic().getState().equals(GameState.BATTLE)) {
            // set the animation back to idling
            while (!LitiPlayer.instance().animations().get("test-idle-right").isPlaying()) {
                LitiPlayer.instance().animations().get("test-idle-right").start();
            }
            LitiPlayer.instance().setState(PlayerState.LOCKED);
        } else if (Program.getGameLogic().getState().equals(GameState.INGAME)) {
            // remove BattleAnimation from map.
            BattleAnimationEntity.instance().die();
        }
        Game.loop().detach(forceCheck);
    }

    private interface ForceListener extends IUpdateable {
        default void finished(Force force) {
        }
    }
}
