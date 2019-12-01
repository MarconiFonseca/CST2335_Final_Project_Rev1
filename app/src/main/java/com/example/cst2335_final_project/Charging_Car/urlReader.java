package com.example.cst2335_final_project.Charging_Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class urlReader {
    static String readURL(String theURL) {
        StringBuilder content = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(theURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
//            URLConnection urlConnection = url.openConnection();
            InputStream inputstream = connection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(inputstream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            try {
                if (bufferedReader != null) {

                    bufferedReader.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
