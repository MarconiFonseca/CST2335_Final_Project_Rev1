package com.example.cst2335_final_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.Charging_Car.charging_station;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {
    /**
     * Declaring variables
     */
    private ActionBar toolbar;
    private LstViewAdapter adapter;
    ArrayList<Apps> apps = new ArrayList<>();




    private static final String ACTIVITY_NAME = "main";
    AdapterView listView;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = getSupportActionBar();
        apps.add(new Apps("  Electric Car Charging Station finder", "Lanre", R.drawable.car, 1));
        apps.add(new Apps("  Recipe search engine", "Linh", R.drawable.recipe, 2));
        apps.add(new Apps("  Foreign currency conversion api", "Amir", R.drawable.foreign, 3));
        apps.add(new Apps("  News Api.org headlines api", "Marconi", R.drawable.newapp, 4));

        // Initiate a new Adapter
        adapter = new LstViewAdapter(this, apps);
        //Declaring the listView obj
        final ListView listView = findViewById(R.id.listview_main);
        listView.setAdapter(adapter);
        //Button button = findViewById(R.id.button01);

        Toast.makeText(adapter.getContext(), "CST 2335", Toast.LENGTH_SHORT).show();

        final BottomNavigationView bottomNavigationView = findViewById(R.id.navegation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            /**
             *
             * @param item
             * @return
             */
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.car_station:
                        Intent car = new Intent(MainActivity.this, charging_station.class);

                        startActivity(car);
                        break;
                    case R.id.recipes:
                        Toast.makeText(MainActivity.this, "Recipes", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.currency:
                        Toast.makeText(MainActivity.this, "currency", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.news_api:


                        Intent nextActivity = new Intent(MainActivity.this, com.example.cst2335_final_project.NewsAPI.MainActivity.class);
                        startActivity(nextActivity); //make the transition
                        break;

                }
                return true;
            }



        });

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setCancelable(true);

        builder.setTitle(getString(R.string.dialog));
        builder.setMessage(getString(R.string.message));

        // Setting Negative "Cancel" Button
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "Yes" Button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();

        // listView.removeFooterView(progressBar);
    }


    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });
        return true;
    }


    /**
     *
     */
    @Override
    protected void onResume() {
        super.onResume();
        onRestart();
    }

    /**
     * On Post Resume Method
     */
    @Override
    protected void onPostResume() {
        super.onPostResume();
        onRestart();

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }


    /**
     * Apps Class represents type of desert.
     * Each object has 3 properties: name, developer name,button click and image resource ID.
     */
    class Apps {

        private String vName;
        private int mImageResourceId;
        private String developerN;
        private int clickB;

        /*
         * Create a new App object.
         *
         * @param vName is the name of the API
         * @param developerN is the corresponding name of developers
         * @param image is drawable reference ID that corresponds to the dessert
         * @param clickB is the corresponding button of the list
         * */

        public Apps(String vName, String developerName, int imageResourceId, int click) {
            this.vName = vName;
            mImageResourceId = imageResourceId;
            developerN = developerName;
            clickB = click;
        }



        public String getDeveName() {
            return developerN;
        }


        public String getAppname() {
            return vName;
        }


        public int getImageResourceId() {
            return mImageResourceId;
        }

        public int getClickButton(int i) {

            switch(i) {
                case 3:

                    Intent news = new Intent(MainActivity.this, com.example.cst2335_final_project.NewsAPI.MainActivity.class);

                    startActivity(news);


                    break;

                case 0:

                    Intent car = new Intent(MainActivity.this, charging_station.class);

                    startActivity(car);


                    break;


            }


            return clickB;


        }
    }



}