package com.example.cst2335_final_project.Charging_Car;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;

public class DisplayDB extends AppCompatActivity {



    TextView textView;
    DatabaseHelper dbhelper;
    ArrayList<String> arrys;
    ListView dblist;
    ArrayAdapter <String>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        textView = findViewById(R.id.dbItems);
        dbhelper= new DatabaseHelper(this);
        arrys= new ArrayList<String>();

//        dblist =  findViewById(R.id.dblistView);
        Intent incomingIntent = new Intent();


        String viewData = dbhelper.viewData();

        arrys.add(viewData);

//        adapter = new ArrayAdapter(getApplicationContext(), R.layout.displaydbitems,arrys );
        textView.setText(arrys.toString());

//        dblist.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
    }
}
