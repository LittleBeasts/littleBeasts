package calculationEngine.entities;

import java.util.Random;

public enum Nature {
    PLAYFUL,
    LAZY,
    GLUTTONOUS,
    ANGRY,
    FEISTY,
    TIMID;

    public static Nature getRandomNature(){
        Random random = new Random();
        Nature[] natures = Nature.values();
        return natures[random.nextInt(natures.length)];
    }

}
