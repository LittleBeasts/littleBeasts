package utilities;

import org.json.JSONObject;

import java.io.*;

public class JsonReader {

    public static JSONObject readJson(String fileName) {
        String sJson = "";
        try (BufferedReader br = new BufferedReader(new FileReader(new File(fileName)))) {
            String str;
            while ((str = br.readLine()) != null) {
                sJson += str + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string2json(sJson);
    }

    public static JSONObject string2json(String str) {
        JSONObject jsonObject = new JSONObject(str);
        return jsonObject;
    }
}

