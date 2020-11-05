package com.littleBeasts.entities;

import calculationEngine.entities.Beasts;
import calculationEngine.entities.CeEntity;
import com.littleBeasts.screens.BeastStats;
import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

public class Beast extends Creature implements IUpdateable {
    private CeEntity littleBeast;
    private String monsterName;
    private Image portrait;
    private BeastStats beastStats;
    private int x, y;
    private boolean spwaned;

    public Beast(Beasts beasts, int x, int y, Direction facingDirection) {
        // super(beasts.name());
        super("test");
        this.portrait = Resources.images().get("sprites/icon.png");
        this.littleBeast = new CeEntity(beasts);
        this.monsterName = monsterName;
        this.x = x;
        this.y = y;
        Spawnpoint e = new Spawnpoint(x, y);
        this.setFacingDirection(facingDirection);
        e.spawn(this);
    }

    public void removeFromMap() {
        this.setVisible(false);
    }

    public void addToMap(int x, int y) {
        this.setVisible(true);
        this.setLocation(x, y);
    }

    public void createBeastStats(int x, int y, int width, int height) {
        this.beastStats = new BeastStats(x, y, width, height, this);
    }

    @Override
    public void update() {
        if (!spwaned) {
            Spawnpoint spawnpoint = new Spawnpoint(x, y);
            spawnpoint.spawn(this);
            spwaned = true;
        }

    }

    public CeEntity getLittleBeast() {
        return littleBeast;
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
}
