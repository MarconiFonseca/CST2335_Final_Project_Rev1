package com.example.cst2335_final_project.Charging_Car;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;


public class viewData extends AppCompatActivity {

    private static Custom_List_Adapter adapter;
    ArrayList<Charging> arrys;
    DatabaseHelper dbhelper;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        lv=findViewById(R.id.showDbList);
        dbhelper= new DatabaseHelper(this);

        arrys = new ArrayList<>();
        arrys=dbhelper.viewData();

        adapter = new Custom_List_Adapter(getApplicationContext(), R.layout.activity_display_db, arrys);
        lv.setAdapter(adapter);
    }

}
