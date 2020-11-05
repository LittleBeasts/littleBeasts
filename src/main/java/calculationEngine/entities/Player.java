package calculationEngine.entities;

import calculationEngine.environment.Regions;

import java.util.List;

public class Player extends CeEntity {

    private List<CeEntity> team;
    private boolean isAI = false;

    public Player(Nature nature, Attack[] attacks, int hitPoints, int maxHitPoints, int level, int friendshipPoints, int speed, int stamina, int attack, int defense, int developmentLvl, List<CeEntity> team) {
        super(BeastTypes.PlayerStandard, nature, attacks, hitPoints, maxHitPoints, level, friendshipPoints, speed, stamina, attack, defense, developmentLvl, false);
        this.team = team;
    }

    public List<CeEntity> getTeam() {
        return team;
    }

    public boolean isAI() {
        return isAI;
    }

    public CeEntity getCeEntity(){
        return new CeEntity(this.getType(), this.getNature(), this.getAttacks(), this.getHitPoints(), this.getMaxHitPoints(), this.getLevel(), this.getFriendshipPoints(), this.getSpeed(), this.getStamina(), this.getAttack(), this.getDefense(), this.getDevelopmentLvl(), false);
    }

}
