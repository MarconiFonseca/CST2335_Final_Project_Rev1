package com.example.cst2335_final_project.Charging_Car;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;

public class Preview extends AppCompatActivity {

    TextView preview;
    Button loadInMap,addToFav;
    DatabaseHelper dbhelper;
    ArrayList<Charging> addToFavs;

    String tittle, Latitude,Longitude,Contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        preview = findViewById(R.id.preview);
        loadInMap = findViewById(R.id.loadInMap);
        addToFav = findViewById(R.id.addtoFav);
        dbhelper= new DatabaseHelper(this);
        addToFavs= new ArrayList<>();

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
            loadMap();
        });
        addToFav.setOnClickListener(v -> {
//            dbhelper.deleteAll();
            if(tittle==null && Latitude==null && Longitude==null && Contact==null){
                Toast.makeText(this, "Empty Values unacceptable", Toast.LENGTH_SHORT).show();
            }else{
                dbhelper.insertData(tittle, Latitude, Longitude, Contact);
                addToFavs.add(new Charging(tittle, Double.parseDouble(Latitude),Double.parseDouble(Longitude), Contact));

                Toast.makeText(this, "Successfully added to the Database", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadMap(){
        String mapCoordinates= "geo:"+Latitude+","+Longitude;
        Uri gmmIntentUri = Uri.parse(mapCoordinates);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW);
        mapIntent.setData(gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }
}