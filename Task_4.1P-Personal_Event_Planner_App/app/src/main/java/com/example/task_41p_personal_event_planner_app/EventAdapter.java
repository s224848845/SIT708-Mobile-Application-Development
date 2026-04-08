package com.example.task_41p_personal_event_planner_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private final Context context;
    private List<Event> events = new ArrayList<>();
    // Listener used to handle Edit and Delete button clicks from the fragment.
    private OnEventClickListener listener;

    public interface OnEventClickListener {
        void onEditClick(Event event);
        void onDeleteClick(Event event);
    }

    public EventAdapter(Context context) {
        this.context = context;
    }

    public void setOnEventClickListener(OnEventClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate one event item layout for the RecyclerView.
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        // Bind one Event object to the current RecyclerView row.
        Event currentEvent = events.get(position);
        holder.textTitle.setText(currentEvent.getTitle());
        holder.textCategory.setText(currentEvent.getCategory());
        holder.textLocation.setText(currentEvent.getLocation());
        holder.textDateTime.setText(DateTimeUtils.formatDateTime(currentEvent.getDateTime()));

        // Forward Edit button clicks to the fragment.
        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditClick(currentEvent);
            }
        });

        // Forward Delete button clicks to the fragment.
        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteClick(currentEvent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEvents(List<Event> events) {
        // Replace the current list and refresh the RecyclerView.
        this.events = events;
        notifyDataSetChanged();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        private final TextView textTitle;
        private final TextView textCategory;
        private final TextView textLocation;
        private final TextView textDateTime;
        private final Button btnEdit;
        private final Button btnDelete;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            // Bind all UI components inside one event card.
            textTitle = itemView.findViewById(R.id.textTitle);
            textCategory = itemView.findViewById(R.id.textCategory);
            textLocation = itemView.findViewById(R.id.textLocation);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
