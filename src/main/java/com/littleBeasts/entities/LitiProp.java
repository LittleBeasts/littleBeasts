package com.littleBeasts.entities;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationListener;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.gui.SpeechBubble;
import de.gurkenlabs.litiengine.gui.SpeechBubbleAppearance;

import java.util.Collection;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiProp {

    PropState propState;
    private String name;
    private IEntity iEntity;
    private Prop prop;

    public LitiProp(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.prop = Game.world().environment().getProp(this.name);
        this.propState = PropState.CLOSED;
    }

    public void interact() {
        if (!prop.isDead()) {
            prop.hit(50);
            Collection<Animation> animations = prop.animations().getAll();
            for (Animation animation : animations) {
                animation.setLooping(false);
            }
            IEntityAnimationController<?> animationController = prop.animations();
            animationController.addListener(new AnimationListener() {
                @Override
                public void played(Animation animation) {
                    AnimationListener.super.played(animation);
                }

                @Override
                public void finished(Animation animation) {
                    AnimationListener.super.finished(animation);
                    if (animation.getName().equals("damaged")) {
                        prop.die();
                        propState = PropState.OPENED;
                        if (DEBUG_CONSOLE_OUT)
                            System.out.println("I contained: " + prop.getProperties().getProperty("item").getAsString());
                        SpeechBubble.create(iEntity, "I contained: " + prop.getProperties().getProperty("item").getAsString());
                        if (!prop.hasCollision())
                            prop.setCollision(true);
                        animationController.detach();
                    }
                }
            });
        }
    }

    private enum PropState {
        CLOSED,
        OPENED
    }

    @Override
    public String toString() {
        return "LitiProp{" +
                "propState=" + propState +
                ", name='" + name + '\'' +
                '}';
    }
}


