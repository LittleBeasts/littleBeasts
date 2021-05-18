package utilities;

import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonReader {

    public static JSONObject readJson(String fileName) {
        InputStream inputStream = JsonReader.class.getResourceAsStream(fileName);
        StringBuilder sJson = new StringBuilder();
        try {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String str;
                while ((str = br.readLine()) != null) {
                    sJson.append(str).append("\n");
                }
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