package com.example.cst2335_final_project.Charging_Car;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

            public class DisplayDB extends AppCompatActivity {
            TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_db);
        textView = findViewById(R.id.textView2);
        Intent incomingIntent = new Intent();

      String dbData =  incomingIntent.getStringExtra("db");

        textView.setText(dbData);


    }
}
