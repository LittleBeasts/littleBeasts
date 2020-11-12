package com.littleBeasts.entities;

import calculationEngine.entities.CeBeasts;
import calculationEngine.entities.CeEntity;
import com.littleBeasts.screens.BeastStats;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

@CollisionInfo(collisionBoxWidth = 14, collisionBoxHeight = 30, collision = true)
public class Beast extends Creature implements IUpdateable {
    private final CeEntity ceEntity;
    private String monsterName;
    private final Image portrait;
    private BeastStats beastStats;
    private int x, y;
    private boolean spwaned;
    private final boolean playerBeast;
    private Spawnpoint e;

    public Beast(CeBeasts beasts, int x, int y, boolean playerBeast) {
        super(beasts.name());
        System.out.println("Name:" + beasts.name());
        this.portrait = Resources.images().get("sprites/icon.png"); // ToDo: Add to Enum of Beasts
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

    public void createBeastStats(int x, int y, int width, int height) {
        this.beastStats = new BeastStats(x, y, width, height, this, playerBeast);
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
