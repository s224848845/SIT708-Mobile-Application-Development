package com.example.task_51c_media__content_apps.sportsnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.listener.OnNewsClickListener;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.List;

public class LatestNewsAdapter extends RecyclerView.Adapter<LatestNewsAdapter.ViewHolder> {

    private final List<NewsItem> list;
    private final OnNewsClickListener listener;

    public LatestNewsAdapter(List<NewsItem> list, OnNewsClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LatestNewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latest_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LatestNewsAdapter.ViewHolder holder, int position) {
        NewsItem item = list.get(position);

        holder.ivImage.setImageResource(item.getImageResId());
        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.cardView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvCategory;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivLatestImage);
            tvTitle = itemView.findViewById(R.id.tvLatestTitle);
            tvCategory = itemView.findViewById(R.id.tvLatestCategory);
            cardView = itemView.findViewById(R.id.cardLatest);
        }
    }
}