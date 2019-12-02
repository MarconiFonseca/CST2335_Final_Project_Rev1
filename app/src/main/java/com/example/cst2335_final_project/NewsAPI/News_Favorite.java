package com.example.cst2335_final_project.NewsAPI;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * News_Favorite Class
 */


public class News_Favorite extends AppCompatActivity {

    /**
     * Declaring Variables
     */

    private List<Article> articleList = new ArrayList<>();
    private ListView newsArticleFavouritesListView;
    private Button deleteFromFavourites;
    private favorite_adapter adapter1;
    private SQLiteDatabase db;
    private ImageButton deletebutton;

    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    boolean isTablet;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites_news);

        articleList = new ArrayList<>();
        newsArticleFavouritesListView = findViewById(R.id.favourites_list_view);
        adapter1 = new favorite_adapter (this, R.layout.favorites_rows,articleList);
        newsArticleFavouritesListView.setAdapter(adapter1);
        deletebutton = findViewById(R.id.delete);
        MyDatabaseOpenHelper dbOpener = new MyDatabaseOpenHelper(this);
        db = dbOpener.getWritableDatabase();
        loadFavourites();

        newsArticleFavouritesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Article item = (Article) parent.getItemAtPosition(position);
                /**
                 * call function to start the details activity
                 * pass in the selected article object
                 * @param item
                 */
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getUrl()));
                startActivity(browserIntent);
            }
        });
    }

    /**
     * Load Favorite Page
     */
    private void loadFavourites() {
        String[] columns = {MyDatabaseOpenHelper.COL_ID, MyDatabaseOpenHelper.COL_TITLE, MyDatabaseOpenHelper.COL_DESCRIPTION,
                MyDatabaseOpenHelper.COL_ARTICLEURL, MyDatabaseOpenHelper.COL_IMAGEURL};
        Cursor results = db.query(false, MyDatabaseOpenHelper.TABLE_NAME, columns, null, null, null, null, null, null);

        int titleColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_TITLE);
        int descriptionColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_DESCRIPTION);
        int idColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ID);
        int articleUrlColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_ARTICLEURL);
        int imageUrlColumnIndex = results.getColumnIndex(MyDatabaseOpenHelper.COL_IMAGEURL);
        while (results.moveToNext()) {

            String title = results.getString(titleColumnIndex);
            String description = results.getString(descriptionColumnIndex);
            String url = results.getString(articleUrlColumnIndex);
            String urltoImage = results.getString(imageUrlColumnIndex);
            long id = results.getLong(idColumnIndex);


            articleList.add(new Article(id, title, url, urltoImage, description));
        }
        adapter1.notifyDataSetChanged();
    }


}
