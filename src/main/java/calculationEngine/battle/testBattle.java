package calculationEngine.battle;

import calculationEngine.entities.*;
import config.BattleConstants;

import java.util.ArrayList;
import java.util.List;

public class testBattle {

    public static void main(String[] args) throws InterruptedException {
        simulateAiBattle();
    }

    private static void simulateAiBattle() throws InterruptedException {

        List<CeEntity> team = new ArrayList<>();
        team.add(new CeEntity(Beasts.StinkenderFeuerFurz));

        CePlayer cePlayer1 = new CePlayer(Nature.ANGRY, new Attack[]{new Attack(Attacks.Punch)}, 1,1,1,1,1,1,1,1,1,team);
        cePlayer1.setActiveMonsterIndex(0);
        CeAi cePlayer2 = new CeAi(cePlayer1);
        Battle battle = new Battle(cePlayer1, cePlayer2);
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn().getNumber() == cePlayer1.getNumber()) {
                    System.out.println("Turn of: Player 1");
                    CeEntity[] returned = battle.useAttack(new Attack(Attacks.Punch));
                    for (CeEntity entitiy : returned
                    ) {
                        System.out.println(entitiy.toString());
                    }
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }

    }

    private static void simulate2PlayerBattle() throws InterruptedException {
        List<CeEntity> team = new ArrayList<>();
        team.add(new CeEntity(Beasts.StinkenderFeuerFurz));

        CePlayer cePlayer1 = new CePlayer(Nature.ANGRY, new Attack[]{new Attack(Attacks.Punch)}, 1,1,1,1,1,1,1,1,1,team);
        CePlayer cePlayer2 = new CePlayer(Nature.ANGRY, new Attack[]{new Attack(Attacks.Punch)}, 1,1,2,1,1,1,1,1,1,team);
        cePlayer1.setActiveMonsterIndex(0);
        cePlayer2.setActiveMonsterIndex(0);
        Battle battle = new Battle(cePlayer1, cePlayer2);
        battle.start();
        System.out.println("Battle started");

        while (true){
            if(battle.getTurn() != null){
                if (battle.getTurn() != BattleConstants.noneTurnCePlayer) {
                    System.out.println("Turn of player:" + (battle.getTurn() == cePlayer1 ? "1" : "2"));
                    CeEntity[] returned = battle.useAttack(new Attack(Attacks.Punch));
                    for (CeEntity entitiy : returned
                    ) {
                        System.out.println(entitiy.toString());
                    }
                }
            }
            else {System.out.println("End of fight"); break;}
            Thread.sleep(10);
        }
    }

}
