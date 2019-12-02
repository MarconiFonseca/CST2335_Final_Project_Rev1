package com.example.cst2335_final_project.NewsAPI;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;


public class News_Adapter extends BaseAdapter implements Filterable {

    private List<Article> articleslist;
    private List<Article> articleslistClone;
    private MyDatabaseOpenHelper dbOpener;


    private Context mContext;
    private int layoutResourceId;

    /**
     *
     * @param mContext
     * @param layoutResourceId
     * @param articleslist
     */

    public News_Adapter(Context mContext, int layoutResourceId, List<Article> articleslist) {
        super();
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.articleslist = articleslist;
        articleslistClone = new ArrayList<>(articleslist);

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

    /**
     *
     * @param position
     * @return
     */
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

        final boolean[] favorites = new boolean[0];


         View row = view;


        final Article articleModel = articleslist.get(position);


        final ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.text = row.findViewById(R.id.article_adapter_tv_title);
            holder.descriptionTextView = row.findViewById(R.id.article_adapter_tv_description);
            holder.imageView = row.findViewById(R.id.image_view);
            holder.imageButton = row.findViewById(R.id.star);


            row.setTag(holder);

            dbOpener = new MyDatabaseOpenHelper((Activity) mContext);
            holder.db = dbOpener.getWritableDatabase();
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        dbOpener = new MyDatabaseOpenHelper((Activity) mContext);
                        holder.db = dbOpener.getWritableDatabase();
                        ContentValues newRowValues = new ContentValues();
                        newRowValues.put(MyDatabaseOpenHelper.COL_TITLE, articleModel.getTitle());
                        newRowValues.put(MyDatabaseOpenHelper.COL_DESCRIPTION, articleModel.getDescription());
                        newRowValues.put(MyDatabaseOpenHelper.COL_ARTICLEURL, articleModel.getUrl());
                        newRowValues.put(MyDatabaseOpenHelper.COL_IMAGEURL, articleModel.getUrlToImage());
                        long newId = holder.db.insert(MyDatabaseOpenHelper.TABLE_NAME, null, newRowValues);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        AlertDialog dialog = builder.setMessage("Added Article to Favourites")
                                .setPositiveButton("OK", (d, w) -> {  /* nothing */})
                                .create();
                        dialog.show();
                        holder.imageButton.setBackgroundDrawable(ContextCompat.getDrawable(mContext.getApplicationContext(), R.drawable.img_star_yellow));


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

        row.setOnClickListener(v -> {

            if(articleModel.getUrl() != null) {

                Log.e("clicked url", articleModel.getUrl());
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("url", articleModel.getUrl());
                mContext.startActivity(intent);

            }



            });
       return row;

    }

    /**
     *
     * @return
     */
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(articleslist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article item : articleslist) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        /**
         *
         * @param constraint
         * @param results
         */
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articleslist.clear();
            articleslist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    private static class ViewHolder {
        TextView text;
        ImageView imageView;
        TextView descriptionTextView;
        ToggleButton  toggleButton;
        ImageButton imageButton;
        SQLiteDatabase db;





    }

}