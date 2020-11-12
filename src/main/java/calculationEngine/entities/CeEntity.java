package calculationEngine.entities;

import calculationEngine.environment.CeRegions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CeEntity {
// |--------------------------------------------------------------------------|
// |    This class is the parent class for every littleBeast.                 |
// |    Every Beast will inherit the props and funcs of this class and        |
// |    extend them.                                                          |
// |--------------------------------------------------------------------------|

    // properties
    private List<CeAttack> ceAttacks;

    // development logic isn't implemented yet -> has to be decided if we want to use this.
    private CeBeasts evolution; // Development beast
    private final int evolutionLvl; // level at which development will take place

    // stats
    private CeStats ceStats;

    private final boolean wild;
    private final Random random = new Random();
    private List<Integer> damages = new ArrayList<>();

    private int playerNumber;

    public CeEntity(CeStats ceStats, List<CeAttack> ceAttacks, int developmentLvl, boolean wild) {
        this.ceAttacks = ceAttacks;
        this.ceStats = ceStats;
        this.evolutionLvl = developmentLvl;
        this.wild = wild;
    }

    public CeEntity(CeRegions region, CeEntity player) { // Constructor for new Encounter Beast
        CeBeasts beast = pickBeast(region);
        this.ceStats = new CeStats(beast, player.getCeStats().getLevel());
        this.evolutionLvl = beast.getEvolutionlvl();
        this.ceAttacks = pickAttacks();
        this.wild = true;
    }

    public CeEntity(CeBeasts beast) { // dev constructor
        this.ceStats = new CeStats(beast);
        this.ceAttacks = new ArrayList<>();
        this.ceAttacks.add(new CeAttack(CeAttacks.Punch));
        this.evolutionLvl = beast.getEvolutionlvl();
        this.wild = true;
    }

    // Constructor needed for AI-Creation because of inheritance structure
    public CeEntity() {
        this.evolutionLvl = Integer.MAX_VALUE;
        this.wild = false;
    }

    private CeBeasts pickBeast(CeRegions region) {
        CeBeasts[] allBeasts = CeBeasts.values();
        List<CeBeasts> availableBeasts = new ArrayList<>();
        for (CeBeasts beast : allBeasts) {
            if (beast.getRegion() == region) availableBeasts.add(beast);
        }
        return availableBeasts.get(random.nextInt(availableBeasts.size()));
    }

    private List<CeAttack> pickAttacks() {
        List<CeAttack> pickedCeAttacks = new ArrayList<>();
        List<CeAttacks> typedAttacks = new ArrayList<>();
        for (CeAttacks attack : CeAttacks.values()) {
            if (attack.getType() == this.ceStats.getType()) {
                typedAttacks.add(attack);
            }
        }
        for (int i = 0; i < 4; i++) {
            if (typedAttacks.size() == 0) {
                break;
            } else {
                int index = random.nextInt(typedAttacks.size());
                pickedCeAttacks.add(new CeAttack(typedAttacks.remove(index)));
            }
        }

        return pickedCeAttacks;
    }

    public int getEvolutionLvl() {
        return evolutionLvl;
    }

    public CeStats getCeStats() {
        return ceStats;
    }

    public CeBeasts getEvolution() {
        return evolution;
    }

    public List<CeAttack> getAttacks() {
        return ceAttacks;
    }

    public boolean isWild() {
        return wild;
    }

    public void setHitPoints(int hitPoints) {
        this.ceStats.setCurrentHitPoints(hitPoints);
    }

    public void dealDamage(int damage) {
        int newHitPoints = this.ceStats.getCurrentHitPoints() - damage;
        this.ceStats.setCurrentHitPoints(newHitPoints);
        this.damages.add(damage);
    }

    public List<Integer> getDamages() {
        List<Integer> tmp = new ArrayList<>(damages);
        damages.clear();
        return tmp;
    }

    public void setCeStats(CeStats ceStats) {
        this.ceStats = ceStats;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public String toString() {
        return "CeEntity{" +
                "type=" + this.ceStats.getType() +
                ", nature=" + this.ceStats.getNature() +
                ", attacks=" + Arrays.toString(ceAttacks.toArray()) +
                ", development=" + evolution +
                ", developmentLvl=" + evolutionLvl +
                ", maxHitPoints=" + this.ceStats.getMaxHitPoints() +
                ", hitPoints=" + this.ceStats.getCurrentHitPoints() +
                ", level=" + this.ceStats.getLevel() +
                ", friendshipPoints=" + this.ceStats.getFriendshipPoints() +
                ", speed=" + this.ceStats.getSpeed() +
                ", stamina=" + this.ceStats.getStamina() +
                ", attack=" + this.ceStats.getAttack() +
                ", defense=" + this.ceStats.getDefense() +
                ", wild=" + wild +
                ", random=" + random +
                '}';
    }
}
