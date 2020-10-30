package calculationEngine;


import static utilities.JsonReader.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Random;

public class Loot {


    private static final int dropCommon = 60;
    private static final int dropRare = 35;
    private static final int dropLegendary = 5;

    private static JSONObject jsonObject = readJson("JSON/loottable.JSON");
    private static JSONObject jsonContainerTable = new JSONObject(jsonObject.get("containerTable").toString());
    private static JSONObject jsonItemTable = new JSONObject(jsonObject.get("itemTable").toString());

    public static JSONObject getLootBySource(String source) {
        JSONObject drop = new JSONObject(jsonContainerTable.get(source).toString());
        Random rdm = new Random();
        List<Object> probabilities = drop.getJSONArray("probabilities").toList();

        int roll = rdm.nextInt(101);
        int amountItems = 0;
        int cumulativeProbability = 0;
        for (Object o : probabilities) {
            int iProb = Integer.valueOf((String) o);
            if (roll > cumulativeProbability) {
                amountItems++;
                cumulativeProbability += iProb;
            }
        }
        String[] items = new String[amountItems];
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < items.length; i++) {
            roll = rdm.nextInt(101);
            if (roll < dropCommon) {
                items[i] = getDrop("common");
            } else if (roll >= dropCommon && roll < dropCommon + dropRare) {
                items[i] = getDrop("rare");
            } else {
                items[i] = getDrop("legendary");
            }
            jsonArray.put(items[i]);
            jsonObject.put(String.valueOf(i), items[i]);
        }
        return jsonObject;
    }

    private static String getDrop(String rarity) {
        Random rdm = new Random();
      //  System.out.println(jsonItemTable.get(rarity));
        JSONObject itemTable = (JSONObject) jsonItemTable.get(rarity);
        List<Object> oDropTable = itemTable.getJSONArray("items").toList();
        int roll = rdm.nextInt(oDropTable.size());
        return oDropTable.get(roll).toString();
    }
}
