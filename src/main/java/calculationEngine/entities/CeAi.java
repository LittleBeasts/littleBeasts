package calculationEngine.entities;

import calculationEngine.battle.CeBattle;
import calculationEngine.environment.CeRegions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CeAi extends CePlayer implements Runnable {
    private CeEntity currentMonster;
    private CeBattle battle;

    // Constructor for new Random Beast AI
    public CeAi(CePlayer player, CeRegions region) {
        super();
        CeEntity ceBeast = new CeEntity(region, player);
        finishAIConstruction(ceBeast);
    }

    // Constructor for Special defined Beast AI
    public CeAi(CeEntity ceEntity) {
        super();
        finishAIConstruction(ceEntity);
    }

    // Constructor for NPC with Team
    public CeAi(CePlayer player, CeBeastTypes type, List<CeEntity> team) {
        super();
        this.setTeam(team);
        this.setCeStats(new CeStats(player.getCeStats().getLevel(), type));
    }

    @Override
    public void run() {
        this.currentMonster = this.getTeam().get(0);
        while (battle.isFightOngoing()) {
            System.out.println(battle.isFightOngoing());
            System.out.println("running");
            if (battle.getTurn() != null) {
                if (battle.getTurn().getNumber() == this.getNumber()) {
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
        System.out.println("End of AI Thread");
    }

    private void finishAIConstruction(CeEntity ceBeast){
        List<CeEntity> team = new ArrayList<>();
        team.add(
                ceBeast
        );
        this.setTeam(team);
        System.out.println("AI CREATION: " + ceBeast.toString());
        this.setCeStats(new CeStats(ceBeast.getCeStats()));
        this.getCeStats().setMaxHitPoints(0);
        this.getCeStats().setCurrentHitPoints(0);
    }

    private CeAttack pickAttack() {
        List<CeAttack> ceAttacks = this.currentMonster.getAttacks();
        Random random = new Random();
        int ind = random.nextInt(ceAttacks.size());
        System.out.println(ind);
        return ceAttacks.get(ind);
    }

    public void setBattle(CeBattle battle) {
        this.battle = battle;
    }
}
