package com.example.cst2335_final_project.Charging_Car;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cst2335_final_project.R;

import java.util.ArrayList;

public class Custom_List_Adapter extends ArrayAdapter<Charging> {

    ArrayList<Charging> charging;
    Context context;
    int resource;

    public Custom_List_Adapter(Context context, int resource, ArrayList<Charging> charging) {
        super(context, resource, charging);

        this.charging = charging;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.custom_charge_items, null, true);

        }

        Charging info = getItem(position);

        TextView station_id = convertView.findViewById(R.id.station_tittle);
        station_id.setText("Station Tittle : " + String.valueOf(info.getTitle()));
        station_id.setTextColor(Color.DKGRAY);
        station_id.setTextSize(16);
        TextView longitudes = convertView.findViewById(R.id.station_longitude);
        longitudes.setText("Longitude :" + " " + info.getLongitude().toString());
        longitudes.setTextColor(Color.DKGRAY);
        longitudes.setTextSize(14);

        TextView latitudes = convertView.findViewById(R.id.station_latitude);
        latitudes.setText("Latitude : " + " " + info.getLatitude().toString());
        latitudes.setTextColor(Color.DKGRAY);
        latitudes.setTextSize(14);

        TextView station_number = (TextView) convertView.findViewById(R.id.station_number);
        station_number.setText("Contact : " + info.getPhone_number());
        station_number.setTextColor(Color.DKGRAY);
        station_number.setTextSize(14);
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

//    @Override
//    public void clear() {
//        charging.clear();
//    }

    public long getItemId(long position) {
        return position;
    }



    public Charging getItem(int position) {
        return charging.get(position);
    }

    @Override
    public int getCount() {
        return charging.size();
    }
}