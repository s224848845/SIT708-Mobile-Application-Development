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

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    private final List<NewsItem> list;
    private final OnNewsClickListener listener;

    public FeaturedAdapter(List<NewsItem> list, OnNewsClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_featured_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder holder, int position) {
        NewsItem item = list.get(position);

        holder.ivImage.setImageResource(item.getImageResId());
        holder.tvTitle.setText(item.getTitle());
        holder.cardView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivFeaturedImage);
            tvTitle = itemView.findViewById(R.id.tvFeaturedTitle);
            cardView = itemView.findViewById(R.id.cardFeatured);
        }
    }
}