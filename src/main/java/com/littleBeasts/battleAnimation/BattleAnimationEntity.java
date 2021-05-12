package com.littleBeasts.battleAnimation;

import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.resources.Resources;

import java.util.Collection;

public class BattleAnimationEntity extends Creature implements IMobileEntity {

    private static BattleAnimationEntity battleAnimationEntity;

    public BattleAnimationEntity() {
        Collection<Spritesheet> battleAnimations = Resources.spritesheets().getAll();
        for (Spritesheet spritesheet : battleAnimations) {
            if (spritesheet.getName().contains("battleAnimation")) {
                int[] animationFrames = new int[spritesheet.getColumns() * spritesheet.getRows()];
                System.out.println("Frames: " + animationFrames.length);
                Animation animation = new Animation(spritesheet, false, false, animationFrames);
                animation.setDurationForAllKeyFrames(4);
                this.animations().add(animation);
            }
        }
        this.setName("BattleAnimator");
    }

    public static BattleAnimationEntity instance() {
        if (battleAnimationEntity == null) {
            battleAnimationEntity = new BattleAnimationEntity();
        }
        return battleAnimationEntity;
    }
}
