package com.example.cst2335_final_project.NewsAPI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.GONE;
import static android.widget.Toast.LENGTH_SHORT;
import static com.example.cst2335_final_project.NewsAPI.News_Favorite.ITEM_ID;
import static com.example.cst2335_final_project.NewsAPI.News_Favorite.ITEM_POSITION;
import static com.example.cst2335_final_project.NewsAPI.News_Favorite.ITEM_SELECTED;


public class favorite_adapter extends BaseAdapter {

    private List<Article> articleslist;

    private MyDatabaseOpenHelper dbOpener;



    private Context mContext;
    private int layoutResourceId;

    public favorite_adapter(Context mContext, int layoutResourceId, List<Article> articleslist) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.articleslist = articleslist;

    }

    /**
     *
     * @return
     */
    @Override
    public int getCount() {
        return articleslist.size();
    }

    /**
     * Methods gets the article item at index
     *
     * @param i position of a article in a list
     * @return news article object
     */
    @Override
    public Article getItem(int i) {
        return articleslist.get(i);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    /**
     * manufactures a new view to be placed into the listview as a singular row
     *
     * @return row
     */
    @Override
    public View getView(final int position, final View view, final ViewGroup parent) {

       View row = view;



        final Article articleModel = articleslist.get(position);
        final ViewHolder holder;

        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper((Activity)mContext);


        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.text = row.findViewById(R.id.article_adapter_tv_title);
            holder.descriptionTextView = row.findViewById(R.id.article_adapter_tv_description);
            holder.imageView = row.findViewById(R.id.image_view);
            holder.deleteButton=row.findViewById(R.id.delete);

            row.setTag(holder);

            holder.db = dbOpener.getWritableDatabase();
            holder.deleteButton.setTag(position);

            holder.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = 0;
                    String test = "" + pos;

                    holder.db.delete(MyDatabaseOpenHelper.TABLE_NAME, MyDatabaseOpenHelper.COL_ID + "=?", new String[]{Long.toString(articleslist.get(pos).getId())});
                    articleslist.remove(pos);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "Deleted", LENGTH_SHORT).show();

                }
            });



        } else {
            holder = (ViewHolder) row.getTag();
        }


        if (!TextUtils.isEmpty(Html.fromHtml(articleModel.getTitle()))) {

            holder.text.setText(Html.fromHtml(articleModel.getTitle()));

        } else {

            holder.text.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(articleModel.getDescription())) {
            holder.descriptionTextView.setText(articleModel.getDescription());
        }

        if (!TextUtils.isEmpty(articleModel.getUrlToImage())) {
            Picasso.get().load(articleModel.getUrlToImage())
                    .resize(700, 500)
                    .centerInside()
                    .into(holder.imageView);
        }

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTablet = v.findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded

                    Bundle dataToPass = new Bundle();
                    dataToPass.putString(ITEM_SELECTED, articleslist.get(position).toString());

                    dataToPass.putInt(ITEM_POSITION, position);
                    dataToPass.putLong(ITEM_ID, articleslist.get(position).getId());

                    if(isTablet)
                    {
                        DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
                        dFragment.setArguments( dataToPass ); //pass it a bundle for information
                        dFragment.setTablet(true);
                        FragmentManager fm = ((MainActivity) mContext).getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        ft.add(R.id.fragmentLocation, dFragment);
                         ft.addToBackStack("AnyName");
                        ft.commit();
                                           }
                    else //isPhone
                    {
                        if (articleModel.getUrl() != null) {

                            Log.e("clicked url", articleModel.getUrl());
                            Intent intent = new Intent(mContext, WebActivity.class);
                            intent.putExtra("url", articleModel.getUrl());
                            mContext.startActivity(intent);
                        }
                    }}



       });
      return row;


    }


    private static class ViewHolder {
        TextView text;
        ImageView imageView;
        TextView descriptionTextView;
        SQLiteDatabase db;
        ImageButton deleteButton;





    }









}