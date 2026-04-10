package com.example.task_51c_media__content_apps.istream.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.istream.model.PlaylistItem;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    public interface OnPlaylistClickListener {
        void onPlaylistClick(PlaylistItem item);
    }

    private final List<PlaylistItem> list;
    private final OnPlaylistClickListener listener;

    public PlaylistAdapter(List<PlaylistItem> list, OnPlaylistClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaylistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistAdapter.ViewHolder holder, int position) {
        PlaylistItem item = list.get(position);
        holder.tvUrl.setText(item.videoUrl);
        holder.cardView.setOnClickListener(v -> listener.onPlaylistClick(item));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrl;
        CardView cardView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrl = itemView.findViewById(R.id.tvPlaylistUrl);
            cardView = itemView.findViewById(R.id.cardPlaylist);
        }
    }
}