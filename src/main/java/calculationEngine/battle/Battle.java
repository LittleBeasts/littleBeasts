package calculationEngine.battle;

import calculationEngine.entities.*;
import calculationEngine.environment.Item;
import config.BattleConstants;

import java.util.Random;

public class Battle extends Thread {

    private CeEntity selectedFightEntityPlayer1;
    private CeEntity selectedFightEntityPlayer2;
    private CePlayer cePlayer1;
    private CePlayer cePlayer2;
    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private boolean fightOngoing = true;
    private boolean threadSleep;
    private boolean onServer = false;

    public Battle(CeEntity selectedFightEntityPlayer1, CeEntity selectedFightEntityPlayer2, CePlayer cePlayer1, CePlayer cePlayer2) {
        this.selectedFightEntityPlayer1 = selectedFightEntityPlayer1;
        this.selectedFightEntityPlayer1.setPlayerNumber(1);
        this.selectedFightEntityPlayer2 = selectedFightEntityPlayer2;
        this.selectedFightEntityPlayer2.setPlayerNumber(2);
        this.cePlayer1 = cePlayer1;
        this.cePlayer2 = cePlayer2;
    }

    @Override
    public void run() {
        int maxTickAmount = BattleConstants.tickAmount;
        int tickAmountPlayer1 = maxTickAmount;
        int tickAmountPlayer2 = maxTickAmount;
        System.out.println("Battle Thread Started!");
        while (fightOngoing){
            tickAmountPlayer1 -= selectedFightEntityPlayer1.getSpeed();
            tickAmountPlayer2 -= selectedFightEntityPlayer2.getSpeed();
            if(tickAmountPlayer1 <= 0){
                System.out.println("[THREAD]: PLAYER 1 TURN");
                turnPlayer1 = true;
                tickAmountPlayer1 = maxTickAmount + tickAmountPlayer1;
                threadSleep();
            }
            if (tickAmountPlayer2 <= 0){
                System.out.println("[THREAD]: PLAYER 2 TURN");
                turnPlayer2 = true;
                tickAmountPlayer2 = maxTickAmount + tickAmountPlayer2;
                threadSleep();
            }
        }
        turnPlayer1 = false;
        turnPlayer2 = false;
    }

    public void setSelectedFightEntityPlayer1(CeEntity entity){
        this.selectedFightEntityPlayer1 = entity;
    }

    public void setSelectedFightEntityPlayer2(CeEntity entity){
        this.selectedFightEntityPlayer2 = entity;
    }


    private void setBattleEnd(){
        this.fightOngoing = false;
    }

    public void flee(){
        if((turnPlayer1 && cePlayer2.isAI()) || (turnPlayer2 && cePlayer1.isAI())){
            Random random = new Random();
            if(random.nextInt(2) == 1){
                setBattleEnd();
            }
        }
    }

    public void catchBeast(){
        Random random = new Random();
        if(turnPlayer1){
                Item item = new Item(1);
                Catching.isCaught(cePlayer1.getCeEntity(), selectedFightEntityPlayer2, new Item(0));
        }

        if(turnPlayer2){
                Item item = new Item(1);
                Catching.isCaught(cePlayer2.getCeEntity(), selectedFightEntityPlayer1, new Item(0));
        }
    }

    public CeEntity[] useAttack(Attack attack){
        if(turnPlayer1){
            turnPlayer1 = false;
            return applyAttack(selectedFightEntityPlayer1, selectedFightEntityPlayer2, attack);
        }
        else if (turnPlayer2) {
            turnPlayer2 = false;
            return applyAttack(selectedFightEntityPlayer2, selectedFightEntityPlayer1, attack);

        }
        return new CeEntity[]{null, null};
    }

    private CeEntity[] applyAttack(CeEntity attacker, CeEntity defender, Attack attack){
        int damage =  Damage.calculateDamage(attacker, defender, attack);
        if(damage != -1){
            System.out.println("Damage:" + damage);
            int newHitPoints = defender.getHitPoints() - damage;
            defender.setHitPoints(newHitPoints);
            if(defender.getHitPoints() <= 0){
                defender.setHitPoints(0);
                if(defender.getType() == BeastTypes.PlayerStandard) {
                    if (defender.getPlayerNumber() == 1) {
                        cePlayer1.setHitPoints(0);
                    } else {
                        cePlayer2.setHitPoints(0);
                    }
                    this.fightOngoing = false;
                }
                else{
                    if (defender.getPlayerNumber() == 1) {
                        setSelectedFightEntityPlayer1(cePlayer1.getCeEntity());
                        this.selectedFightEntityPlayer1.setPlayerNumber(1);
                        defender = selectedFightEntityPlayer1;
                    }
                    else{
                        setSelectedFightEntityPlayer2(cePlayer2.getCeEntity());
                        this.selectedFightEntityPlayer2.setPlayerNumber(2);
                        defender = selectedFightEntityPlayer2;
                    }

                }
            }
        }
        else System.out.println("Missed!");
        setActionDone();
        return new CeEntity[]{attacker, defender};

    }

    private void threadSleep(){
        threadSleep = true;
        System.out.println("Thread now sleeping!");
        while (threadSleep){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread continue");
    }

    private void setActionDone(){
        this.threadSleep = false;
    }

    public CePlayer getTurn(){
        if(turnPlayer1) return cePlayer1;
        else if(turnPlayer2) return cePlayer2;
        else if(fightOngoing) return BattleConstants.noneTurnCePlayer;
        else return null;
    }

}
