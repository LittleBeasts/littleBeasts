package calculationEngine.entities;

import java.util.Random;

public enum CeNature {
    PLAYFUL,
    LAZY,
    GLUTTONOUS,
    ANGRY,
    FEISTY,
    TIMID;

    public static CeNature getRandomNature(){
        Random random = new Random();
        CeNature[] natures = CeNature.values();
        return natures[random.nextInt(natures.length)];
    }

}
