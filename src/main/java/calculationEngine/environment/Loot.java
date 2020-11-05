package calculationEngine.environment;


import static utilities.JsonReader.*;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Loot {


    private static final int dropCommon = 60;
    private static final int dropRare = 35;
    private static final int dropLegendary = 5;

    private static JSONObject jsonObject = readJson("JSON/loottable.JSON");
    private static JSONObject jsonContainerTable = new JSONObject(jsonObject.get("containerTable").toString());
    private static JSONObject jsonItemTable = new JSONObject(jsonObject.get("itemTable").toString());
    private static JSONObject itemList = readJson("JSON/items.JSON");

    public static JSONObject[] getLootBySource(String source) {
        JSONObject drop = new JSONObject(jsonContainerTable.get(source).toString());
        JSONObject[] items;
        if (sourceHasItem(drop)) {
            JSONArray drops = drop.getJSONArray("item");
            items = new JSONObject[drops.length()];
            for (int i = 0; i < items.length; i++) {
                items[i] = (JSONObject) itemList.get(drops.get(i).toString());
            }
            return items;
        }
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

        items = new JSONObject[amountItems];
        for (int i = 0; i < items.length; i++) {
            if (sourceHasRarity(drop)) {
                items[i] = getDrop(drop.get("rarity").toString());
                continue;
            }
            roll = rdm.nextInt(101);
            if (roll < dropCommon) {
                items[i] = getDrop("common");
            } else if (roll >= dropCommon && roll < dropCommon + dropRare) {
                items[i] = getDrop("rare");
            } else {
                items[i] = getDrop("legendary");
            }
        }
        return items; //TODO: cast items in item class instead of giving back a JSON.
    }

    private static boolean sourceHasRarity(JSONObject drop) {
        if (drop.get("rarity").toString().equals(""))
            return false;
        else
            return true;
    }

    private static boolean sourceHasItem(JSONObject drop) {
        if (drop.get("item").toString().equals(""))
            return false;
        else
            return true;
    }

    private static JSONObject getDrop(String rarity) {
        Random rdm = new Random();
        //  System.out.println(jsonItemTable.get(rarity));
        JSONObject itemTable = (JSONObject) jsonItemTable.get(rarity);
        JSONArray items = itemTable.getJSONArray("items");
        int roll = rdm.nextInt(101);
        int cumulativeProbability = 0;
        for (Object o : items) {
            JSONObject oItem = (JSONObject) o;
            int iProb = Integer.valueOf((String) oItem.get("probability"));
            cumulativeProbability += iProb;
            if (roll < cumulativeProbability) {
                return oItem;
            }
        }
        return null;
    }
}
