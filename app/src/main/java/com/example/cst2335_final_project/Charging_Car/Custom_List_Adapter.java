package com.example.cst2335_final_project.Charging_Car;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;

public class Custom_List_Adapter extends ArrayAdapter<Charging> {

ArrayList <Charging> charging;
Context context;
int resource;

    public Custom_List_Adapter(Context context, int resource, ArrayList <Charging> charging){
        super(context,resource , charging);

        this.charging = charging;
        this.context = context;
        this.resource = resource;

    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_charge_items, null, true);

        }

        Charging info = getItem(position);

        TextView station_id = (TextView)convertView.findViewById(R.id.station_id);
        station_id.setText(info.getId());

        TextView longitudes = (TextView)convertView.findViewById(R.id.station_longitude);
        longitudes.setText(info.getLongitude());

        TextView latitudes = (TextView)convertView.findViewById(R.id.station_latitude);
        latitudes.setText(info.getLatitude());

        TextView station_number =  (TextView)convertView.findViewById(R.id.station_number);
        station_number.setText(info.getPhone_number());

        return convertView;
    }


}
