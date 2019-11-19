package com.example.cst2335_final_project.Charging_Car;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cst2335_final_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class charging_station extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    Button entertoSearch;
    private  static Custom_List_Adapter adapter;
    ListView lv;
    CharSequence query;
    SearchView latitude;
    SearchView longitude;
    TextView tv;
    ArrayList<Charging> charging_stations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_station);

        //Customizing toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Electric Car Charging");
        setSupportActionBar(toolbar);

        latitude = (SearchView) findViewById(R.id.latitude);
        longitude = (SearchView) findViewById(R.id.longitude);
        charging_stations = new ArrayList<>();


        query = latitude.getQuery();
        entertoSearch = (Button) findViewById(R.id.enter);

        CharSequence latitude_search = latitude.getQuery();
        CharSequence longitude_search = longitude.getQuery();

        entertoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=US&maxresults=10");
            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.charging_menu, menu);
//        return true;
//    }
    private static String readURL(String theURL) {
        StringBuilder content = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader bufferedReader=null;

        try {
            URL url = new URL(theURL);
            connection = (HttpURLConnection)url.openConnection();
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
        }finally {
            if(connection!=null){
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
    class ReadJSON extends AsyncTask<String, Double, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            try {
                JSONArray jArray = new JSONArray(s);
              //  JSONArray jArray = jsonObject.getJSONArray("AddressInfo");
                lv = (ListView) findViewById(R.id.car_listview);
                for (int i = 0; i < jArray.length(); i++) {

                        JSONObject station = jArray.getJSONObject(i);
                    JSONObject carObject = station.getJSONObject("AddressInfo");

                    String tit =   carObject.getString("Title");
                    Double longs =   carObject.getDouble("Latitude");
                    Double lat =   carObject.getDouble("Longitude");
                    String num;
                    String numnull= " ";


                    if(carObject.getString("ContactTelephone1") == numnull){
                        num = "Phone not available";
                    }else{
                        num = carObject.getString("ContactTelephone1");
                    }

                    charging_stations.add(new Charging(tit, longs,lat,num));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
             adapter = new Custom_List_Adapter(
                    getApplicationContext(), R.layout.custom_charge_items, charging_stations);
            lv.setAdapter(adapter);

        }

    }

}





