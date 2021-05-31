package config;

import org.json.JSONObject;

import static utilities.JsonReaderUtils.readJson;

public class LootConstants {

    public static final int dropCommon = 60;
    public static final int dropRare = 35;
    public static final int dropLegendary = 5;
    public static final JSONObject itemList = readJson("/items.JSON");

}

