package utilities;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static JSONObject readJson(InputStream inputStream) {
        StringBuilder sJson = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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

