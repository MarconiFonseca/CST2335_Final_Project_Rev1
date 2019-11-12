package com.example.cst2335_final_project;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class LstViewAdapter extends ArrayAdapter<MainActivity.Apps> {

   // private static Object Context;
    private List<MainActivity.Apps> appsList;
    private List<MainActivity.Apps> apps;


   
    public LstViewAdapter(@NonNull Context context, int resource, List<MainActivity.Apps> apps) {
        super(context, resource);
    }

    public LstViewAdapter(MainActivity context, ArrayList<MainActivity.Apps> apps) {
        super(context, 0, apps);
        this.apps=apps;
        appsList= new ArrayList<>(apps);
    }

    /*
     *  LstViewADapter that can provide the layout for each list.
     *
     * */

  //  private static final String LOG_TAG = LstViewAdapter.class.getSimpleName();

    /**
     * @param context    The current context. Used to inflate the layout file.
     *  api A List of API`s objects to display in a list
     *
     *
     */

//    public LstViewAdapter(Activity context, ArrayList<MainActivity.Apps> api) {
//
//        super(context, 0, api);
//
//
//
//    }





    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */

    @Override
    public View getView( int position,  View convertView, final ViewGroup parent) {

         View listItemView;

            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false
            );

        final MainActivity.Apps currentApi = getItem(position);

        Toast.makeText(getContext(), "CST 2335", Toast.LENGTH_SHORT).show();


        TextView nameTextView = (TextView) listItemView.findViewById(R.id.api_name);


        nameTextView.setText(currentApi.getAppname());

       TextView developersView = (TextView) listItemView.findViewById(R.id.developer);

        developersView.setText(String.valueOf(currentApi.getDeveName()));


        ImageView iconView = (ImageView) listItemView.findViewById(R.id.list_item_icon);

        iconView.setImageResource(currentApi.getImageResourceId());
        //final ProgressBar simpleProgressBar =  listItemView.findViewById(R.id.simpleProgressBar);
       // simpleProgressBar.setVisibility(View.INVISIBLE);
         final ProgressBar progressBar;
        final int progressStatus = 0;
         final TextView textView;
         final Handler handler = new Handler();

        final Button button = listItemView.findViewById(R.id.button01);
        progressBar = listItemView.findViewById(R.id.progressBar);

        textView = (TextView) listItemView.findViewById(R.id.textView);



                //Dessert dessert = desserts.get(i);



        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                    new Thread(new Runnable() {
                    public void run() {
                        int progressStatus = 0;
                        while (progressStatus < 100 ) {
                            progressStatus += 1;
                            // Update the progress bar and display the
                            //current value in the text view

                            final int finalProgressStatus = progressStatus;
                            handler.post(new Runnable() {
                                public void run() {
                                    progressBar.setProgress(finalProgressStatus);
                                    textView.setText(finalProgressStatus +"/"+progressBar.getMax());
                                }
                            });
                            try {
                                // Sleep for 200 milliseconds.
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }if(getItem(0) == currentApi) {
                            currentApi.getClickButton(0);

                        }
                        }
                }).start();






//               if(getItem(0) == currentApi) {
//
//
//
//
//                   currentApi.getClickButton(0);
//
//               }
//
//               if(getItem(1) == currentApi) {
//                   currentApi.getClickButton(1);       }


           }

        });




        return listItemView;
    }


    @Override
    public Filter getFilter() {
        return exempleFilter;
    }
    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MainActivity.Apps> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(appsList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();


                for (MainActivity.Apps item : appsList) {
                    if (item.getAppname().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            apps.clear();
            apps.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };




}