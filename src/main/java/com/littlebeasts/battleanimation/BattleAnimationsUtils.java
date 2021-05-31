package com.littlebeasts.battleanimation;

import de.gurkenlabs.litiengine.entities.IEntity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class BattleAnimationsUtils {

    private static final List<DamageAnimation> damageAnimationList = new ArrayList<>();

    public static void animateDamage(IEntity iEntity, int damage) {
        damageAnimationList.add(new DamageAnimation(iEntity.getCenter(), damage));
    }

    public static void draw(Graphics2D g) {
        for (DamageAnimation damageAnimation : damageAnimationList) {
            damageAnimation.draw(g);
        }
        damageAnimationList.removeIf(DamageAnimation::outOfBounds);
    }

    public static boolean allAnimationsDone() {
        return damageAnimationList.size() == 0;
    }
}
