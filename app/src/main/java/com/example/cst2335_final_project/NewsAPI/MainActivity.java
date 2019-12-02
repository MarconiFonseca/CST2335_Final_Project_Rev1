package com.example.cst2335_final_project.NewsAPI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    /**
     Declaring Variables
    @param articleList
    @param NEWS_URL
    @param task
    @param adapter
    @param listView

     */
    List<Article> articleList = new ArrayList<>();
    private String NEWS_URL;
    private DownloadTask task;
    private News_Adapter adapter;
    private ListView listView;
    private static final String API_KEY = "f0c87052698848f6a32b40eb4630ec16";
    private SharedPreferences sharedPreferences;
    private ToggleButton toggleButton;
    public static final String ITEM_SELECTED = "ITEM";
    public static final String ITEM_POSITION = "POSITION";
    public static final String ITEM_ID = "ID";
    public static final int EMPTY_ACTIVITY = 345;
    SQLiteDatabase db;

    /**
     * On create Method
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_newsapi);
        adapter = new News_Adapter(this, R.layout.row_main_article_adapter,articleList);
        listView = findViewById(R.id.activity_main_rv);
        listView.setAdapter(adapter);
        sharedPreferences = getSharedPreferences("NewsAPI", MODE_PRIVATE);
           }

    /**
     * get data and manipulate it using JSON
     * @param result
     */
    private void representData(String result){


        try {
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("articles");
            Article item;
            Float progress;
            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");
                String image = post.optString("urlToImage");
                String description = post.optString("description");
                String url = post.optString("url");
                item = new Article();
                item.setTitle(title);
                item.setUrlToImage(image);
                item.setUrl(url);
                item.setDescription(description);
                 articleList.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     * AsyncTask to get data from URL
     */
    public class DownloadTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpsURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);

                urlConnection = (HttpsURLConnection) url.openConnection();


                if (result != null) {

                    String response = streamToString(urlConnection.getInputStream());


                    representData(response);


                    return result;


                }
            } catch (MalformedURLException e) {
                e.printStackTrace();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         *
         * @param result
         */

        @Override
        protected void onPostExecute(String result) {

            if (result != null) {
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to load data!", Toast.LENGTH_SHORT).show();
            }

        }
    }
        String streamToString(InputStream newsStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(newsStream));
            String data;
            String result = "";

            while ((data = bufferedReader.readLine()) != null) {
                result += data;
            }

            if (null != newsStream) {
                newsStream.close();
            }


            return result;
        }

    /**
     * Create a Option Menu Bar to use seaching thing
     * @param menu
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.favorite,menu);

        MenuItem favorite = menu.findItem(R.id.favorites);

            favorite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Intent intent =  new Intent(MainActivity.this, News_Favorite.class);
                    startActivity(intent);
                    return false;
                }
            });



                final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        String search;

       //sharedPreferences.getString("search", "");
        search = searchView.getQuery().toString();
        searchView.setQuery(sharedPreferences.getString("search",""),true);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            /**
             *
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("search", searchView.getQuery().toString());
                editor.commit();

                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

                input.hideSoftInputFromWindow(searchView.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                NEWS_URL = "https://newsapi.org/v2/everything?q=" + searchView.getQuery().toString() + "&apiKey=f0c87052698848f6a32b40eb4630ec16";
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Atention : " + searchView.getQuery());
                alertBuilder.setMessage("Do you want to add a new search?");



                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialogInterface
                     * @param i
                     */
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        new DownloadTask().execute(NEWS_URL);
                    }
                });

                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    /**
                     *
                     * @param dialogInterface
                     * @param i
                     */
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        articleList.clear();
                        adapter.notifyDataSetChanged();
                        new DownloadTask().execute(NEWS_URL);

                    }
                });


                if (articleList.size() > 0) {
                    alertBuilder.show();
                } else {
                       new DownloadTask().execute(NEWS_URL);
                }

                adapter.notifyDataSetChanged();
                return false;
            }

            /**
             *
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }



    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "This Interface has been developing by Marconi Fonseca", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                PackageManager manager = this.getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,
                        "PackageName = " + info.packageName + "\nVersionCode = "
                                + info.versionCode + "\nVersionName = "
                                + info.versionName + "\nPermissions = " + info.permissions, Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setCancelable(true);

                builder.setTitle("Instructions how to use this interface");
                builder.setMessage("1- Click in the search bar\n" +
                        "2-Type your desire news\n" +
                        "3- Click on the list to open in the browser");

                // Setting Negative "Cancel" Button
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });

                // Setting Positive "Yes" Button
                builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();

        }
                return true;

    }
}
















