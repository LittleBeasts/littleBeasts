package calculationEngine.entities;

import calculationEngine.battle.Battle;
import config.AiConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CeAi extends Thread {

    private Nature nature;
    private CeAttack[] ceAttacks;
    private int hitPoints;
    private int maxHitPoints;
    private int level;
    private int friendshipPoints;
    private int speed;
    private int stamina;
    private int attack;
    private int defense;
    private int developmentLvl = 0;
    private List<CeEntity> team;

    private CeEntity currentMonster;
    private Battle battle;
    private CePlayer aiPlayer;

    public CeAi(CePlayer player) { //TODO: Change to new random Beast   
        this.team = new ArrayList<>();
        this.team.add(new CeEntity(Beasts.FeuerFurz));
        this.nature = pickNature();
        this.ceAttacks = new CeAttack[]{new CeAttack(Attacks.Punch)};
        this.hitPoints = 0;
        this.maxHitPoints = 0;
        this.level = player.getCeEntity().getLevel();
        this.speed = CeEntity.scaleOnLvl(AiConstants.AI_BASE_SPEED, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.stamina = CeEntity.scaleOnLvl(AiConstants.AI_BASE_STAMINA, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.attack = CeEntity.scaleOnLvl(AiConstants.AI_BASE_ATTACK, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.defense = CeEntity.scaleOnLvl(AiConstants.AI_BASE_DEFENSE, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.friendshipPoints = 0;
        this.aiPlayer = new CePlayer(this.nature, this.ceAttacks, this.hitPoints, this.maxHitPoints, this.level, this.friendshipPoints, this.speed, this.stamina, this.attack, this.defense, this.developmentLvl, this.team);
        this.aiPlayer.setAI();
    }

    public CeAi(CePlayer player, CeEntity ceEntity) {
        this.team = new ArrayList<>();
        this.team.add(ceEntity);
        this.nature = pickNature();
        this.ceAttacks = new CeAttack[]{new CeAttack(Attacks.Punch)};
        this.hitPoints = 0;
        this.maxHitPoints = 0;
        this.level = player.getCeEntity().getLevel();
        this.speed = CeEntity.scaleOnLvl(AiConstants.AI_BASE_SPEED, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.stamina = CeEntity.scaleOnLvl(AiConstants.AI_BASE_STAMINA, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.attack = CeEntity.scaleOnLvl(AiConstants.AI_BASE_ATTACK, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.defense = CeEntity.scaleOnLvl(AiConstants.AI_BASE_DEFENSE, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.friendshipPoints = 0;
        this.aiPlayer = new CePlayer(this.nature, this.ceAttacks, this.hitPoints, this.maxHitPoints, this.level, this.friendshipPoints, this.speed, this.stamina, this.attack, this.defense, this.developmentLvl, this.team);
        this.aiPlayer.setAI();
    }

    public CeAi(CePlayer player, List<CeEntity> team) {
        this.team = team;
        this.nature = pickNature();
        this.ceAttacks = new CeAttack[]{new CeAttack(Attacks.Punch)};
        this.hitPoints = CeEntity.scaleOnLvl(AiConstants.AI_BASE_HP, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.maxHitPoints = this.hitPoints;
        this.level = player.getCeEntity().getLevel();
        this.speed = CeEntity.scaleOnLvl(AiConstants.AI_BASE_SPEED, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.stamina = CeEntity.scaleOnLvl(AiConstants.AI_BASE_STAMINA, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.attack = CeEntity.scaleOnLvl(AiConstants.AI_BASE_ATTACK, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.defense = CeEntity.scaleOnLvl(AiConstants.AI_BASE_DEFENSE, player.getCeEntity().getLevel(), AiConstants.AI_LEVEL_SCALING);
        this.friendshipPoints = 0;
        this.aiPlayer = new CePlayer(this.nature, this.ceAttacks, this.hitPoints, this.maxHitPoints, this.level, this.friendshipPoints, this.speed, this.stamina, this.attack, this.defense, this.developmentLvl, this.team);
        this.aiPlayer.setAI();
    }

    @Override
    public void run() {
        this.currentMonster = this.team.get(0);
        while (true) {
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == this.aiPlayer.getNumber()) {
                    System.out.println("Turn of AI");
                    battle.useAttack(pickAttack());
                }
            } else break;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private CeAttack pickAttack() {
        CeAttack[] ceAttacks = this.currentMonster.getAttacks();
        Random random = new Random();
        return ceAttacks[random.nextInt(ceAttacks.length)];
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    private Nature pickNature() {
        Random rndm = new Random();
        return Nature.values()[rndm.nextInt(Nature.values().length)];
    }

    public CePlayer getAiPlayer() {
        return aiPlayer;
    }

    public List<CeEntity> getTeam() {
        return team;
    }

    public int getActiveMonsterIndex() {
        return this.getAiPlayer().getActiveMonsterIndex();
    }

    public void setActiveMonsterIndex(int index) {
        this.getAiPlayer().setActiveMonsterIndex(index);
    }
}
