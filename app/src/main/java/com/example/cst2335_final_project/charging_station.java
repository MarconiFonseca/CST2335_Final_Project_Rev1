package com.example.cst2335_final_project;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class charging_station extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    ArrayList car_search;
    Toolbar searchToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_station);


        //Customizing toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Electric Car Charging");
        toolbar.setSubtitle("Search by Coordinate");

        setSupportActionBar(toolbar);

        //variables to work with
        listView = (ListView) findViewById(R.id.car_listview);
        searchToolbar = (Toolbar) findViewById(R.id.app_bar_search);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.charging_menu, menu);
return true;
    }

}

