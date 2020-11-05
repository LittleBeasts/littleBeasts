package calculationEngine.battle;

import calculationEngine.entities.*;
import calculationEngine.environment.Item;
import config.BattleConstants;

import java.util.Random;

public class Battle implements Runnable {

    private CeEntity selectedFightEntityPlayer1;
    private CeEntity selectedFightEntityPlayer2;
    private Player player1;
    private Player player2;
    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private boolean fightOngoing;
    private boolean threadSleep;
    private boolean onServer = false;

    public Battle(CeEntity selectedFightEntityPlayer1, CeEntity selectedFightEntityPlayer2, Player player1, Player player2) {
        this.selectedFightEntityPlayer1 = selectedFightEntityPlayer1;
        this.selectedFightEntityPlayer2 = selectedFightEntityPlayer2;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run() {
        int MaxTickAmount = BattleConstants.tickAmount;
        int tickAmountPlayer1 = MaxTickAmount;
        int tickAmountPlayer2 = MaxTickAmount;
        while (fightOngoing){
            tickAmountPlayer1 -= selectedFightEntityPlayer1.getSpeed();
            tickAmountPlayer2 -= selectedFightEntityPlayer2.getSpeed();
            if(tickAmountPlayer1 <= 0){
                turnPlayer1 = true;
                tickAmountPlayer1 = MaxTickAmount;
                threadSleep();
            }
            if (tickAmountPlayer2 <= 0){
                turnPlayer2 = true;
                tickAmountPlayer2 = MaxTickAmount;
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
        if((turnPlayer1 && player2.isAI()) || (turnPlayer2 && player1.isAI())){
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
                Catching.isCaught(player1.getCeEntity(), selectedFightEntityPlayer2, new Item(0));
        }

        if(turnPlayer2){
                Item item = new Item(1);
                Catching.isCaught(player2.getCeEntity(), selectedFightEntityPlayer1, new Item(0));
        }
    }

    public CeEntity[] useAttack(Attack attack){
        if(turnPlayer1){
            int damage = Damage.calculateDamage(selectedFightEntityPlayer1, selectedFightEntityPlayer2, attack);
            int newHitPoints = selectedFightEntityPlayer2.getHitPoints() - damage;
            selectedFightEntityPlayer2.setHitPoints(newHitPoints);
            if(selectedFightEntityPlayer2.getHitPoints() <= 0){
                this.selectedFightEntityPlayer2.setHitPoints(0);
                setSelectedFightEntityPlayer2(player2.getCeEntity());
            }
            setActionDone();
            return new CeEntity[]{selectedFightEntityPlayer1, selectedFightEntityPlayer2};
        }
        else if (turnPlayer2) {
            int damage =  Damage.calculateDamage(selectedFightEntityPlayer2, selectedFightEntityPlayer1,attack);
            int newHitPoints = selectedFightEntityPlayer1.getHitPoints() - damage;
            selectedFightEntityPlayer1.setHitPoints(newHitPoints);
            if(selectedFightEntityPlayer1.getHitPoints() <= 0){
                this.selectedFightEntityPlayer1.setHitPoints(0);
                setSelectedFightEntityPlayer1(player1.getCeEntity());
            }
            setActionDone();
            return new CeEntity[]{selectedFightEntityPlayer2, selectedFightEntityPlayer1};
        }
        return new CeEntity[]{null, null};
    }

    private void threadSleep(){
        threadSleep = true;
        while (threadSleep){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setActionDone(){
        this.threadSleep = false;
    }

    public Player getTurn(){
        if(turnPlayer1) return player1;
        else if(turnPlayer2) return player2;
        else return null;
    }
}
