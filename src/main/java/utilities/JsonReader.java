package utilities;

import org.json.JSONObject;

import java.io.*;

public class JsonReader {

    public static JSONObject readJson(String fileName) {
        StringBuilder sJson = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            String str;
            while ((str = br.readLine()) != null) {
                sJson.append(str).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string2json(sJson.toString());
    }

    public static JSONObject string2json(String str) {
        return new JSONObject(str);
    }
}

