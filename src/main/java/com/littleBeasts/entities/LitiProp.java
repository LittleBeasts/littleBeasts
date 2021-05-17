package com.littleBeasts.entities;

import calculationEngine.environment.CeItem;
import calculationEngine.environment.CeLoot;
import config.FontConstants;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.IEntity;
import de.gurkenlabs.litiengine.entities.Prop;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.graphics.animation.AnimationListener;
import de.gurkenlabs.litiengine.graphics.animation.IEntityAnimationController;
import de.gurkenlabs.litiengine.gui.SpeechBubble;

import java.util.Collection;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

public class LitiProp implements Interactable {

    PropState propState;
    private final String name;
    private final IEntity iEntity;
    private final Prop prop;
    private boolean isStatic;

    public LitiProp(IEntity iEntity) {
        this.name = iEntity.getName();
        this.iEntity = iEntity;
        this.prop = Game.world().environment().getProp(this.name);
        this.propState = PropState.CLOSED;
        this.isStatic = true;
        if (prop.getProperties().getProperty("static") != null)
            this.isStatic = prop.getProperties().getProperty("static").getAsBool();

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
                        String itemName = prop.getProperties().getProperty("item").getAsString();
                        if (DEBUG_CONSOLE_OUT)
                            System.out.println("I contained: " + itemName);
                        SpeechBubble.create(iEntity, "I contained: " + itemName, SpeechBubble.DEFAULT_APPEARANCE, FontConstants.DEFAULT_FONT);
                        CeItem ceItem = CeLoot.lootItem(itemName);
                        System.out.println(ceItem.getName());
                        //try {
                        //    LitiPlayer.instance().getCeInventory().addItemToInventory();
                        //} catch (NoPlaceInInventoryException e) {
                        //    e.printStackTrace();
                        //}
                        if (!prop.hasCollision())
                            prop.setCollision(true);
                        animationController.detach();
                        if (!isStatic)
                            Game.world().environment().remove(iEntity);
                    }
                }
            });
        }
    }

    @Override
    public IEntity getiEntity() {
        return this.iEntity;
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


