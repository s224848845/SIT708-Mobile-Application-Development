package com.example.task_51c_media__content_apps.sportsnews.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.sportsnews.listener.OnNewsClickListener;
import com.example.task_51c_media__content_apps.sportsnews.model.NewsItem;

import java.util.List;

public class RelatedStoriesAdapter extends RecyclerView.Adapter<RelatedStoriesAdapter.ViewHolder> {

    private final List<NewsItem> list;
    private final OnNewsClickListener listener;

    public RelatedStoriesAdapter(List<NewsItem> list, OnNewsClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RelatedStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_related_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatedStoriesAdapter.ViewHolder holder, int position) {
        NewsItem item = list.get(position);

        holder.tvTitle.setText(item.getTitle());
        holder.tvCategory.setText(item.getCategory());
        holder.cardView.setOnClickListener(v -> listener.onNewsClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvCategory;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRelatedTitle);
            tvCategory = itemView.findViewById(R.id.tvRelatedCategory);
            cardView = itemView.findViewById(R.id.cardRelated);
        }
    }
}