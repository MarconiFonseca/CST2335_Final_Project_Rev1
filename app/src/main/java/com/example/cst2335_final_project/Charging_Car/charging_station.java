package com.example.cst2335_final_project.Charging_Car;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cst2335_final_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class charging_station extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Custom_List_Adapter adapter;
    Toolbar toolbar;
    Button entertoSearch;
    ListView lv;
    EditText lat;
    EditText log;
    ArrayList<Charging> charging_stations;
    String lat_text;
    String long_text;
    DatabaseHelper dbhelper;
    Button loadFav ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Electric Car Charging");
        setSupportActionBar(toolbar);
        charging_stations = new ArrayList<>();
        lat = findViewById(R.id.lat);
        log = findViewById(R.id.longs);
        loadFav = findViewById(R.id.fav);
        entertoSearch = findViewById(R.id.enter);
        lv = (ListView) findViewById(R.id.car_listview);

        dbhelper=new DatabaseHelper(this);

        entertoSearch.setOnClickListener(v -> {
            lat_text = lat.getText().toString();
            long_text = log.getText().toString();
            if (lat_text.matches("") || long_text.matches("")) {
                Toast.makeText(charging_station.this, "Fields cannot be empty ", Toast.LENGTH_SHORT).show();
                return;
            }
            new ReadJSON().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude=" + lat_text + "&longitude=" + long_text + "&maxresults=10");
            lat_text.isEmpty();
            long_text.isEmpty();
        });

        lv.setOnItemClickListener((parent, view, position, id) -> {

            String tittle = charging_stations.get(position).getTitle().toString();
            String latitude = charging_stations.get(position).getLatitude().toString();
            String longitide = charging_stations.get(position).getLongitude().toString();
            String contact = charging_stations.get(position).getPhone_number().toString();

            String mapCoordinates= "geo:"+latitude+","+longitide;
            Uri gmmIntentUri = Uri.parse(mapCoordinates);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
          //  startActivity(mapIntent);

        dbhelper.insertData(tittle,latitude,longitide,contact);

//Toast.makeText(this, "Added to the database" + dbhelper.,Toast.LENGTH_LONG).show();
        });

        loadFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(charging_station.this, DisplayDB.class);

                intent.putExtra("db", dbhelper.viewData());

                startActivityForResult(intent, 0);
            }
        });
    }

    class ReadJSON extends AsyncTask<String, Double, String> {
        @Override
        protected String doInBackground(String... strings) {
            return urlReader.readURL(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
            try {
                JSONArray jArray = new JSONArray(s);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject station = jArray.getJSONObject(i);
                    JSONObject carObject = station.getJSONObject("AddressInfo");
                    String tit = carObject.getString("Title");
                    Double longs = carObject.getDouble("Latitude");
                    Double lat = carObject.getDouble("Longitude");
                    String num;
                    String numnull = " ";

                    if (carObject.getString("ContactTelephone1") == numnull) {
                        num = "Phone not available";
                    } else {
                        num = carObject.getString("ContactTelephone1");
                    }
                    charging_stations.add(new Charging(tit, longs, lat, num));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new Custom_List_Adapter(getApplicationContext(), R.layout.custom_charge_items, charging_stations);
            lv.setAdapter(adapter);
        }
    }

    public void showDialog()
    {

        View dialogtext = getLayoutInflater().inflate(R.layout.dialog, null);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Button loadInMap= (Button) findViewById(R.id.loadMap);
        Button addToFav = (Button) findViewById(R.id.addtoFav) ;


        builder.setView(dialogtext);
        builder.create().show();
    }

}
