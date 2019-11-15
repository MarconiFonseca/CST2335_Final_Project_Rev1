package com.example.cst2335_final_project.NewsAPI;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cst2335_final_project.NewsAPI.API.ApiClient;
import com.example.cst2335_final_project.NewsAPI.API.ApiInterfaces;
import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.NewsAPI.News_API_Models.News;
import com.example.cst2335_final_project.NewsAPI.utils.OnRecyclerViewItemClickListener;
import com.example.cst2335_final_project.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity_NewsAPI extends AppCompatActivity implements OnRecyclerViewItemClickListener {

    List<Article> articleList = new ArrayList<>();

    Adapter_NewsAPI adapter;
    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;


    private static final String API_KEY = "f0c87052698848f6a32b40eb4630ec16";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_newsapi);

        final RecyclerView recyclerView = findViewById(R.id.activity_main_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new Adapter_NewsAPI(articleList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        Snackbar.make(recyclerView, "This is a SnackBar", Snackbar.LENGTH_LONG).show();
        final ApiInterfaces apiService = ApiClient.getClient().create(ApiInterfaces.class);


        Call<News> call = apiService.getLatestNews("ca", API_KEY);
        call.enqueue(new Callback<News>() {


            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if (articleList.size() > 0) {
                        final Adapter_NewsAPI adapter = new Adapter_NewsAPI(articleList);

                        adapter.setOnRecyclerViewItemClickListener(MainActivity_NewsAPI.this);
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e("out", t.toString());
            }


        });


    }

    @Override
    public void onItemClick(int position, View view) {
        switch (view.getId()) {
            case R.id.article_adapter_ll_parent:
                Article article = (Article) view.getTag();
                if (!TextUtils.isEmpty(article.getUrl())) {
                    Log.e("clicked url", article.getUrl());
                    Intent webActivity = new Intent(this, WebActivity.class);
                    webActivity.putExtra("url", article.getUrl());
                    startActivity(webActivity);
                }
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_news, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}


















