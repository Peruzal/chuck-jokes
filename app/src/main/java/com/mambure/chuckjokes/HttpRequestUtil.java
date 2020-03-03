package com.mambure.chuckjokes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequestUtil {

    static String makeRequest(String requestUrl) {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(requestUrl);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.connect();
            InputStream inputStream =  connection.getInputStream();
            String response = readInputStream(inputStream);
            return parseJSON(response);

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }finally {
            connection.disconnect();
        }
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        return builder.toString();

    }

    private static String parseJSON(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String joke = jsonObject.getString("value");
            return joke;
        } catch (JSONException e) {
            e.printStackTrace();
            return "Error occured, try again";
        }
    }
}
