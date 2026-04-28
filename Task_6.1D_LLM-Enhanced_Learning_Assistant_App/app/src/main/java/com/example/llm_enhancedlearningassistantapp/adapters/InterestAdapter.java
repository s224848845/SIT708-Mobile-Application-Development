package com.example.llm_enhancedlearningassistantapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.models.Interest;

import java.util.List;

public class InterestAdapter extends RecyclerView.Adapter<InterestAdapter.InterestViewHolder> {

    private final List<Interest> interestList;

    public InterestAdapter(List<Interest> interestList) {
        this.interestList = interestList;
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest, parent, false);
        return new InterestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        Interest interest = interestList.get(position);
        holder.tvInterest.setText(interest.getName());
        holder.cardView.setCardBackgroundColor(
                interest.isSelected() ? 0xFF00E676 : 0xFFFFFFFF
        );

        holder.itemView.setOnClickListener(v -> {
            interest.setSelected(!interest.isSelected());
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return interestList.size();
    }

    static class InterestViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvInterest;

        public InterestViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardInterest);
            tvInterest = itemView.findViewById(R.id.tvInterest);
        }
    }
}