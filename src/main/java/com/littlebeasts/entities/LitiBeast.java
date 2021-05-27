package com.littlebeasts.entities;

import calculationEngine.entities.CeBeasts;
import calculationEngine.entities.CeEntity;
import com.littlebeasts.battleanimation.BeastStats;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

import static config.GlobalConfig.DEBUG_CONSOLE_OUT;

@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 30, collision = true)
public class LitiBeast extends Creature implements IUpdateable {
    private final CeEntity ceEntity;
    private String monsterName;
    private final Image portrait;
    private BeastStats beastStats;
    private int x, y;
    private boolean spwaned;
    private final boolean playerBeast;
    private Spawnpoint e;

    public LitiBeast(CeBeasts beasts, int x, int y, boolean playerBeast) {
        super(beasts.name());
        if (DEBUG_CONSOLE_OUT) System.out.println("Name:" + beasts.name());
        this.portrait = Resources.images().get(beasts.getPortrait());
        this.ceEntity = new CeEntity(beasts);
        this.monsterName = monsterName; //tbd
        this.x = x;
        this.y = y;
        this.playerBeast = playerBeast;
        e = new Spawnpoint(x, y);
        e.spawn(this);
        if (!playerBeast) {
            this.beastStats = new BeastStats(this, false);
        }
    }

    public void createBeastStats() {
        this.beastStats = new BeastStats(this, playerBeast);
    }

    @Override
    public void update() {
        if (!spwaned) {
            Spawnpoint spawnpoint = new Spawnpoint(x, y);
            spawnpoint.spawn(this);
            spwaned = true;
        }
    }


    public CeEntity getCeEntity() {
        return ceEntity;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public Image getPortrait() {
        return portrait;
    }

    public BeastStats getBeastStats() {
        return beastStats;
    }

    public Spawnpoint getE() {
        return e;
    }
}
