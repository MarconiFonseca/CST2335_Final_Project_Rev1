package com.example.cst2335_final_project.NewsAPI;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst2335_final_project.NewsAPI.News_API_Models.Article;
import com.example.cst2335_final_project.NewsAPI.utils.OnRecyclerViewItemClickListener;
import com.example.cst2335_final_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter_NewsAPI extends RecyclerView.Adapter<Adapter_NewsAPI.ViewHolder> implements Filterable {
    private List<Article> articleArrayList;
    private List<Article> articleArrayListFull;

    private Context context;
    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public Adapter_NewsAPI(List<Article> articleArrayList) {
        this.articleArrayList = articleArrayList;
        articleArrayListFull = new ArrayList<>(articleArrayList);
    }

    @Override
    public Adapter_NewsAPI.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_main_article_adapter, viewGroup, false);
        return new Adapter_NewsAPI.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Article articleModel = articleArrayList.get(position);

        if(!TextUtils.isEmpty(articleModel.getUrlToImage())){
            Picasso.get().load(articleModel.getUrlToImage())
                    .resize(700,500)
                    .centerInside()
                    .into(viewHolder.imageview);
        }

        if (!TextUtils.isEmpty(articleModel.getTitle())) {
            viewHolder.titleText.setText(articleModel.getTitle());
        }


        if (!TextUtils.isEmpty(articleModel.getDescription())) {
            viewHolder.descriptionText.setText(articleModel.getDescription());
        }

        viewHolder.artilceAdapterParentLinear.setTag(articleModel);
    }

    @Override
    public int getItemCount() {
        return articleArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return exempleFilter;
    }
    private Filter exempleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Article> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(articleArrayList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Article item : articleArrayList) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
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
            articleArrayList.clear();
            articleArrayList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private TextView descriptionText;
        private LinearLayout artilceAdapterParentLinear;
        private ImageView imageview;

        ViewHolder(View view) {
            super(view);
            imageview = view.findViewById(R.id.image_view);
            titleText = view.findViewById(R.id.article_adapter_tv_title);
            descriptionText = view.findViewById(R.id.article_adapter_tv_description);
            artilceAdapterParentLinear = view.findViewById(R.id.article_adapter_ll_parent);
            artilceAdapterParentLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onRecyclerViewItemClickListener != null) {
                        onRecyclerViewItemClickListener.onItemClick(getAdapterPosition(), view);
                    }
                }
            });
        }
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}