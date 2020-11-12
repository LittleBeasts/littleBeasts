package calculationEngine.battle;

import calculationEngine.entities.*;
import calculationEngine.environment.CeRegions;
import config.BattleConstants;

import java.util.ArrayList;
import java.util.List;

public class testBattle {

    public static void main(String[] args) throws InterruptedException {
        simulateAiBattle();
        // simulateCatch();
    }

    private static void simulateCatch() throws InterruptedException {

        List<CeEntity> team = new ArrayList<>();
        CePlayer cePlayer1 = new CePlayer(new CeStats(CeBeastTypes.PlayerStandard, CeNature.ANGRY, 1,100,100,50,200,200,200,1), new CeAttack[]{new CeAttack(CeAttacks.Punch)},team, false);

        team.add(new CeEntity(CeRegions.ArkhamCity, cePlayer1));
        cePlayer1.setTeam(team);
        cePlayer1.setActiveMonsterIndex(0);
        CeAi cePlayer2 = new CeAi(cePlayer1);
        CeBattle battle = new CeBattle(cePlayer1, cePlayer2);
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn().getNumber() == cePlayer1.getNumber()) {
                    System.out.println("Turn of: Player 1");
                    boolean caught = battle.catchBeast();
                    if (caught) System.out.println("Beast caught! CONGRATS");
                    else System.out.println("Beast doesn't like you");
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }
    }

    private static void simulateAiBattle() throws InterruptedException {

        List<CeEntity> team = new ArrayList<>();
        CePlayer cePlayer1 = new CePlayer(new CeStats(CeBeastTypes.PlayerStandard, CeNature.ANGRY, 1,100,100,50,200,200,200,1), new CeAttack[]{new CeAttack(CeAttacks.Punch)},team, false);
        team.add(new CeEntity(CeRegions.ArkhamCity, cePlayer1));
        cePlayer1.setTeam(team);
        cePlayer1.setActiveMonsterIndex(0);
        CeAi cePlayer2 = new CeAi(cePlayer1);
        CeBattle battle = new CeBattle(cePlayer1, cePlayer2);
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn().getNumber() == cePlayer1.getNumber()) {
                    System.out.println("Turn of: Player 1");
                    System.out.println(cePlayer1.getCeStats().getCurrentHitPoints());
                    battle.useAttack(new CeAttack(CeAttacks.Punch));
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }

    }
}
