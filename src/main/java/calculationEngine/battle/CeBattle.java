package calculationEngine.battle;

import calculationEngine.CeExecuterService;
import calculationEngine.entities.*;
import calculationEngine.environment.CeItem;
import config.BattleConstants;

import java.util.Random;

public class CeBattle implements Runnable {

    private CeEntity selectedFightEntityPlayer1;
    private CeEntity selectedFightEntityPlayer2;
    private final CePlayer cePlayer1;
    private final CePlayer cePlayer2;
    private boolean turnPlayer1;
    private boolean turnPlayer2;
    private boolean fightOngoing = true;
    private boolean threadSleep;
//    private boolean onServer = false; // Maybe we need this for Server specific Logic


    public CeBattle(CePlayer cePlayer1, CePlayer cePlayer2) {
        System.out.println("Running main constructor");
        this.selectedFightEntityPlayer1 = cePlayer1.getTeam().get(cePlayer1.getActiveMonsterIndex());
        this.selectedFightEntityPlayer1.setPlayerNumber(1);
        this.selectedFightEntityPlayer2 = cePlayer2.getTeam().get(cePlayer2.getActiveMonsterIndex());
        this.selectedFightEntityPlayer2.setPlayerNumber(2);
        this.cePlayer1 = cePlayer1;
        this.cePlayer2 = cePlayer2;
        this.cePlayer1.setNumber(1);
        this.cePlayer2.setNumber(2);
        CeExecuterService.addThreadToExecutor(this);
    }

    public CeBattle(CePlayer cePlayer1, CeAi cePlayer2) {
        this(cePlayer1, (CePlayer) cePlayer2);
        System.out.println("Constructor2");
        cePlayer2.setBattle(this);
        CeExecuterService.addThreadToExecutor(cePlayer2);
    }

    public boolean isFightOngoing() {
        return fightOngoing;
    }

    @Override
    public void run() {
        final int maxTickAmount = BattleConstants.tickAmount;
        int tickAmountPlayer1 = maxTickAmount;
        int tickAmountPlayer2 = maxTickAmount;
        System.out.println("Battle Thread Started!");
        while (this.fightOngoing) {
            System.out.println("[THREAD] " + this.fightOngoing);
            tickAmountPlayer1 -= selectedFightEntityPlayer1.getCeStats().getSpeed();
            tickAmountPlayer2 -= selectedFightEntityPlayer2.getCeStats().getSpeed();
            if (tickAmountPlayer1 <= 0) {
                System.out.println("HP of EP1: " + selectedFightEntityPlayer1.getCeStats().getCurrentHitPoints());
                System.out.println("[THREAD]: PLAYER 1 TURN");
                turnPlayer1 = true;
                System.out.println("HP of EP2: " + selectedFightEntityPlayer2.getCeStats().getCurrentHitPoints());
                tickAmountPlayer1 = maxTickAmount + tickAmountPlayer1;
                threadSleep();
            }
            if (tickAmountPlayer2 <= 0) {
                System.out.println("HP of EP2: " + selectedFightEntityPlayer2.getCeStats().getCurrentHitPoints());
                System.out.println("[THREAD]: PLAYER 2 TURN");
                turnPlayer2 = true;
                System.out.println("HP of EP1: " + selectedFightEntityPlayer1.getCeStats().getCurrentHitPoints());
                tickAmountPlayer2 = maxTickAmount + tickAmountPlayer2;
                threadSleep();
            }
        }
        turnPlayer1 = false;
        turnPlayer2 = false;
    }

    public void setSelectedFightEntityPlayer1(CeEntity entity) {
        this.selectedFightEntityPlayer1 = entity;
    }

    public void setSelectedFightEntityPlayer2(CeEntity entity) {
        this.selectedFightEntityPlayer2 = entity;
    }


    private void setBattleEnd() {
        this.fightOngoing = false;
        System.out.println(fightOngoing);
    }

    public void flee() { //ToDo: in Progress
        if ((turnPlayer1 && cePlayer2.isAI()) || (turnPlayer2 && cePlayer1.isAI())) {
            Random random = new Random();
            if (random.nextInt(2) == 1) {
                setBattleEnd();
            }
        }
    }

    public boolean catchBeast() {
        System.out.println("Ce_Catch");
        boolean caught = false;
        if (turnPlayer1) {
            turnPlayer1 = false;
            CeItem item = new CeItem(1);
            caught = CeCatching.isCaught(cePlayer1, selectedFightEntityPlayer2, new CeItem(0));
            if (caught) setBattleEnd();
            setActionDone();
        }
        return caught;
    }

    public void useAttack(CeAttack ceAttack) {
        if (turnPlayer1) {
            turnPlayer1 = false;
            applyAttack(selectedFightEntityPlayer1, selectedFightEntityPlayer2, ceAttack);
        } else if (turnPlayer2) {
            turnPlayer2 = false;
            applyAttack(selectedFightEntityPlayer2, selectedFightEntityPlayer1, ceAttack);

        }
    }

    private void applyAttack(CeEntity attacker, CeEntity defender, CeAttack ceAttack) {
        final int damage = CeDamage.calculateDamage(attacker, defender, ceAttack);
        if (damage != -1) {
            System.out.println("Damage: " + damage);
            defender.dealDamage(damage);
            if (defender.getCeStats().getType() == CeBeastTypes.PlayerStandard) {
                System.out.println("Dealing Damage to player");
                if (defender.getPlayerNumber() == 1) cePlayer1.dealDamage(damage);
                else cePlayer2.dealDamage(damage);
            }
            if (defender.getCeStats().getCurrentHitPoints() <= 0) {
                defender.setHitPoints(0);
                if (defender.getCeStats().getType() == CeBeastTypes.PlayerStandard) {
                    if (defender.getPlayerNumber() == 1) {
                        cePlayer1.setHitPoints(0);
                    } else {
                        cePlayer2.setHitPoints(0);
                    }
                    setBattleEnd();
                } else {
                    if (defender.getPlayerNumber() == 1) {
                        setSelectedFightEntityPlayer1(cePlayer1);
                        this.selectedFightEntityPlayer1.setPlayerNumber(1);
                        defender = selectedFightEntityPlayer1;
                    } else {
                        setSelectedFightEntityPlayer2(cePlayer2);
                        this.selectedFightEntityPlayer2.setPlayerNumber(2);
                        defender = selectedFightEntityPlayer2;
                        if (selectedFightEntityPlayer2.getCeStats().getCurrentHitPoints() == 0) {
                            System.out.println("IM DOIN SOMETHING WITH MY USELESS LIFE");
                            setBattleEnd();
                        }
                    }

                }
            }
        } else{ System.out.println("Missed!");}
        setActionDone();
    }

    private void threadSleep() {
        threadSleep = true;
        System.out.println("Thread now sleeping!");
        while (threadSleep) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread continue");
    }

    private void setActionDone() {
        this.threadSleep = false;
    }

    public CePlayer getTurn() {
        if (turnPlayer1) return cePlayer1;
        else if (turnPlayer2) return cePlayer2;
        else if (fightOngoing) return BattleConstants.noneTurnCePlayer;
        else return null;
    }
}
