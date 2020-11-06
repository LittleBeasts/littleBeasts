package calculationEngine.entities;

import config.PlayerConfig;

import java.util.List;

public class CePlayer extends CeEntity {

    private List<CeEntity> team;
    private boolean isAI = false;
    private int playerNumber;
    private int activeMonsterIndex = 0;
    private Attack[] playerStandardAttacks = PlayerConfig.PLAYER_STANDARD_ATTACKS;

    public CePlayer(Nature nature, Attack[] attacks, int hitPoints, int maxHitPoints, int level, int friendshipPoints, int speed, int stamina, int attack, int defense, int developmentLvl, List<CeEntity> team) {
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

    public void setAI(){
        this.isAI = true;
    }

    public void setNumber(int number){
        this.playerNumber = number;
    }

    public int getNumber(){
        return playerNumber;
    }

    public void setActiveMonsterIndex(int activeMonsterIndex) {
        if(activeMonsterIndex < team.size() && activeMonsterIndex > 0) this.activeMonsterIndex = activeMonsterIndex;
    }

    public int getActiveMonsterIndex() {
        return activeMonsterIndex;
    }

    public Attack[] getPlayerStandardAttacks() {
        return playerStandardAttacks;
    }
}
