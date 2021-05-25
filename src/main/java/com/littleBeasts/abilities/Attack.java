package com.littleBeasts.abilities;

import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.EffectApplication;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.abilities.effects.ForceEffect;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityPivotType;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.physics.Force;

@AbilityInfo(cooldown = 500, origin = EntityPivotType.COLLISIONBOX_CENTER, duration = 300, value = 540)
public class Attack extends Ability {

    public Attack(Creature executor) {
        super(executor);

        this.addEffect(new PunchEffect(this));
    }

    private class PunchEffect extends ForceEffect {

        protected PunchEffect(Ability ability) {
            super(ability, ability.getAttributes().value().get(), EffectTarget.EXECUTINGENTITY);
        }

        @Override
        protected Force applyForce(IMobileEntity affectedEntity) {
            System.out.println("applyForce");
            return null;
        }

        @Override
        protected boolean hasEnded(final EffectApplication appliance) {
            return super.hasEnded(appliance);
        }


    }


}
