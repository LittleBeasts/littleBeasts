package com.littlebeasts.abilities;

import calculationEngine.entities.CeAttack;
import calculationEngine.entities.CeEntity;

import java.io.File;

public interface Ability {

    //TODO improve

    CeAttack getName();

    void use(CeEntity attacker, CeEntity defender);

    void animate(File animationSource, CeEntity attacker, CeEntity defender);

}
