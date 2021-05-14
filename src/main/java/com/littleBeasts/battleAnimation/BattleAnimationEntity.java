package com.littleBeasts.battleAnimation;

import de.gurkenlabs.litiengine.Align;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.*;
import de.gurkenlabs.litiengine.graphics.RenderType;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.resources.Resources;

import java.util.Collection;

import static config.HudConstants.BATTLE_ANIMATION_KEYFRAME_DURATION;


@EntityInfo(width = 16, height = 16)
@MovementInfo(velocity = 100)
@CollisionInfo(collisionBoxWidth = 0, collisionBoxHeight = 0, collision = false, align = Align.CENTER, valign = Valign.DOWN, collisionType = Collision.DYNAMIC)
public class BattleAnimationEntity extends Creature implements IMobileEntity {

    private static BattleAnimationEntity battleAnimationEntity;

    public BattleAnimationEntity() {
        Collection<Spritesheet> battleAnimations = Resources.spritesheets().getAll();
        for (Spritesheet spritesheet : battleAnimations) {
            if (spritesheet.getName().contains("battleAnimation")) {
                int[] animationFrames = new int[spritesheet.getColumns() * spritesheet.getRows()];
                Animation animation = new Animation(spritesheet, false, false, animationFrames);
                animation.setDurationForAllKeyFrames(BATTLE_ANIMATION_KEYFRAME_DURATION);
                this.animations().add(animation);
            }
        }
        this.setRenderType(RenderType.OVERLAY);
        this.setName("BattleAnimator");
    }

    public static BattleAnimationEntity instance() {
        if (battleAnimationEntity == null) {
            battleAnimationEntity = new BattleAnimationEntity();
        }
        return battleAnimationEntity;
    }


}
