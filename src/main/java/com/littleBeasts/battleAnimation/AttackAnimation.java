package com.littleBeasts.battleAnimation;

import com.littleBeasts.Program;
import com.littleBeasts.entities.LitiPlayer;
import com.littleBeasts.gameLogic.GameState;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.physics.Force;

import java.awt.geom.Point2D;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class AttackAnimation {

    IMobileEntity iMobileEntity;

    public AttackAnimation(IMobileEntity iMobileEntity) {
        this.iMobileEntity = iMobileEntity;
    }

    // ToDo: extract to Attack Animation class or similar
    @Action(description = "This is a punch, it hurts.")
    public void punch() {
        iMobileEntity.attachControllers();
        iMobileEntity.movement().attach();
        Point2D origin = iMobileEntity.getCollisionBoxCenter();
        Point2D target = new Point2D.Double(origin.getX() + 50, origin.getY());
        Force force = new Force(target, 200, 1);
        force.setCancelOnCollision(true);
        iMobileEntity.movement().apply(force);
        if (DEBUG_CONSOLE_OUT) System.out.println(origin.toString());
        if (DEBUG_CONSOLE_OUT) System.out.println(target.toString());

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

    private void getBack(Point2D origin) {
        origin = new Point2D.Double(origin.getX() - 7, origin.getY());
        Force force1 = new Force(origin, 100, 1);
        iMobileEntity.movement().apply(force1);

        Runnable applyForce = () -> {
            while (!force1.hasEnded()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (Program.getGameLogic().getState().equals(GameState.BATTLE)) {

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
}
