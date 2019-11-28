package com.example.cst2335_final_project.Charging_Car;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;

public class Preview extends AppCompatActivity {

    DatabaseHelper dbhelper;

    TextView preview;
    Button loadInMap ;
    Button addToFav ;
    ArrayList<String> arrys;
    TextView showDb;
    Button loadDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        preview = findViewById(R.id.preview);
        loadInMap = findViewById(R.id.loadInMap);
        addToFav = findViewById(R.id.addtoFav);
        dbhelper= new DatabaseHelper(this);
        showDb = findViewById(R.id.showDbList);
        loadDb= findViewById(R.id.ViewData);


//        Intent intent = new Intent();

//        String tittle= intent.getStringExtra("tittle");
//        String Latitude =intent.getStringExtra("latitude");
//        String Longitude =intent.getStringExtra("longitude");
//        String Contact=  intent.getStringExtra("contact");
//
//        String contactInfo= tittle + Latitude +Longitude + Contact;

//        preview.setText(contactInfo);

        String tittle;
        String Latitude;
        String Longitude;
        String Contact;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                tittle= null;
                Latitude= null;
                Longitude= null;
                Contact= null;
            } else {

                tittle= extras.getString("tittle");
                Latitude= extras.getString("latitude");
                Longitude= extras.getString("longitude");
                Contact= extras.getString("contact");
            }
        } else {
            tittle= (String) savedInstanceState.getSerializable("tittle");
            Latitude= (String) savedInstanceState.getSerializable("latitude");
            Longitude= (String) savedInstanceState.getSerializable("longitude");
            Contact= (String) savedInstanceState.getSerializable("contact");
        }

                String contactInfo= "Tittle: "+ tittle + "\n"+ "Latitude: "+ Latitude + "\n" +"Longitude: " +Longitude + "\n" +"Contact: " + Contact;

                preview.setText(contactInfo);


        loadInMap.setOnClickListener(v -> {
            String mapCoordinates= "geo:"+Latitude+","+Longitude;
            Uri gmmIntentUri = Uri.parse(mapCoordinates);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW);
            mapIntent.setData(gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        });

        addToFav.setOnClickListener(v -> {
            dbhelper.insertData(tittle, Latitude, Longitude, Contact);
            Toast.makeText(this, "Successfully added to the Database", Toast.LENGTH_SHORT).show();
        });


        loadDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String display = dbhelper.viewData();

                showDb.setText(display);

            }
        });
    }


}
