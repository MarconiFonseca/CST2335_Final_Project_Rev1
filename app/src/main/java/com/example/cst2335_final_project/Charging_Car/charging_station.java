package com.example.cst2335_final_project.Charging_Car;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.cst2335_final_project.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class charging_station extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    Button entertoSearch;
    ArrayList <String> fav ;
    CharSequence query;
    SearchView latitude;
    SearchView longitude;
    public static TextView data;


    TextView tv ;



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
        listView = (ListView) findViewById(R.id.car_listview);
        fav = new   ArrayList<String>();
        query = latitude.getQuery() ;
        entertoSearch = (Button) findViewById(R.id.enter);

      entertoSearch.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              new JsonTask().execute("https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude=45.8371591&longitude=-78.3791239&maxresults=10&compact=true&verbose=false\"");

          }
      });


    }



 @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.charging_menu, menu);

        return true;
    }

    public class JsonTask extends AsyncTask<String, String, String>{


        @Override
        protected String doInBackground(String... urls) {
            String data =null;

//            String apiUrl = "view-source:https://api.openchargemap.io/v3/poi/?output=json&countrycode=CA&latitude=45.8371591&longitude=-78.3791239&maxresults=10&compact=true&verbose=false";
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL (urls[0]);
                connection = (HttpURLConnection)url.openConnection();
                connection.connect();
                InputStream inputstream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputstream));
                String line =" ";
                StringBuffer buffer = new StringBuffer();

                while((line =reader.readLine())!=null){
                    buffer.append(line);


                }
        return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }  finally {
                if(connection!=null){
                    connection.disconnect();
                }

                try {
                    if (reader != null) {

                        reader.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null ;
        }

        @Override
        protected void onPostExecute(String s) {


            tv = (TextView) findViewById(R.id.checking);
        tv.setText(s.toString());
            super.onPostExecute(s);
        }

    }


    }

