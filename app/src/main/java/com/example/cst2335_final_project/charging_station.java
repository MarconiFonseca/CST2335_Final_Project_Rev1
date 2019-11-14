package com.example.cst2335_final_project;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;


public class charging_station extends AppCompatActivity {

    Toolbar toolbar;
    ListView listView;
    Button enter;
    ArrayList fav;
    CharSequence query;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_station);

        //Customizing toolbar.
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Electric Car Charging");

        setSupportActionBar(toolbar);

        searchView = (SearchView) findViewById(R.id.search);
        query = searchView.getQuery();
        enter = (Button) findViewById(R.id.enter);

        enter.setOnClickListener(v ->
//            fav.add(query))
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show());
        ;
//            Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show());
//  clickMeBtn.setOnClickListener(view ->
//            Toast.makeText(MainActivity.this,
//            "This is the way to click a button to make a toast with RetroLambda !", Toast.LENGTH_LONG).show());
}


 @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.charging_menu, menu);


        return true;
    }

}

