package com.example.cst2335_final_project.Charging_Car;

import android.os.AsyncTask;
import android.os.Bundle;
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
    ArrayList<Charging> charging_stations;
    ListView lv;
    CharSequence query;
    SearchView latitude;
    SearchView longitude;
    TextView tv;


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

        lv = (ListView) findViewById(R.id.car_listview);
        charging_stations = new ArrayList<>();
        query = latitude.getQuery();
        entertoSearch = (Button) findViewById(R.id.enter);

        CharSequence latitude_search = latitude.getQuery();
        CharSequence longitude_search = longitude.getQuery();


//        entertoSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////             AsyncTask<String, Integer, String> a = new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude= "+longitude_search+"&longitude=\"+latitude_search+\"&maxresults=10&compact=true&verbose=false\"");
////            new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude="+longitude_search+"&longitude=\"+latitude_search+\"&maxresults=10&compact=true&verbose=false\"");
//            new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=US&maxresults=10&compact=true&verbose=false");
//    }});
//   }});
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//             AsyncTask<String, Integer, String> a = new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude= "+longitude_search+"&longitude=\"+latitude_search+\"&maxresults=10&compact=true&verbose=false\"");
//            new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude="+longitude_search+"&longitude=\"+latitude_search+\"&maxresults=10&compact=true&verbose=false\"");
                new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=US&maxresults=10&compact=true&verbose=false");
            }});

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
    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return readURL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jArray = jsonObject.getJSONArray("AddressInfo");

                for (int i = 0; i < jArray.length(); i++) {
                        JSONObject carObject = jArray.getJSONObject(i);
                        charging_stations.add(new Charging(
                                carObject.getInt("ID"),
                                carObject.getInt("Latitude"),
                                carObject.getInt("Longitude"),
                                carObject.getString("ContactTelephone1")
                        ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Custom_List_Adapter adapter = new Custom_List_Adapter(
                    getApplicationContext(), R.layout.custom_charge_items, charging_stations);
            lv.setAdapter(adapter);
        }

    }

}





