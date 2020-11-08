package calculationEngine.battle;

import calculationEngine.entities.*;
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

        team.add(new CeEntity(BeastTypes.Fire, Nature.LAZY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 10,  100, 1, 0, 30, 10, 100, 10, Integer.MAX_VALUE, true));

        CePlayer cePlayer1 = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 1,1,3,1,50,1,200,1,1,team);
        cePlayer1.setActiveMonsterIndex(0);
        CeAi cePlayer2 = new CeAi(cePlayer1);
        Battle battle = new Battle(cePlayer1, cePlayer2);
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
        team.add(new CeEntity(BeastTypes.Fire, Nature.LAZY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 100,  100, 1, 0, 30, 10, 100, 10, Integer.MAX_VALUE, false));

        CePlayer cePlayer1 = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 1,1,1,1,1,1,1,1,1,team);
        cePlayer1.setActiveMonsterIndex(0);
        CeAi cePlayer2 = new CeAi(cePlayer1);
        Battle battle = new Battle(cePlayer1, cePlayer2);
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn().getNumber() == cePlayer1.getNumber()) {
                    System.out.println("Turn of: Player 1");
                    battle.useAttack(new CeAttack(Attacks.Punch));
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }

    }

    private static void simulate2PlayerBattle() throws InterruptedException {
        List<CeEntity> team = new ArrayList<>();
        team.add(new CeEntity(Beasts.StinkenderFeuerFurz));

        CePlayer cePlayer1 = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 1,1,1,1,1,1,1,1,1,team);
        CePlayer cePlayer2 = new CePlayer(Nature.ANGRY, new CeAttack[]{new CeAttack(Attacks.Punch)}, 1,1,2,1,1,1,1,1,1,team);
        cePlayer1.setActiveMonsterIndex(0);
        cePlayer2.setActiveMonsterIndex(0);
        Battle battle = new Battle(cePlayer1, cePlayer2);
        battle.start();
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn() != BattleConstants.noneTurnCePlayer) {
                    System.out.println("Turn of player:" + (battle.getTurn() == cePlayer1 ? "1" : "2"));
                    battle.useAttack(new CeAttack(Attacks.Punch));
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }
    }

}
