package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.adapters.InterestAdapter;
import com.example.llm_enhancedlearningassistantapp.data.DummyDataProvider;
import com.example.llm_enhancedlearningassistantapp.models.Interest;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InterestsActivity extends AppCompatActivity {

    private List<Interest> interests;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        prefManager = new PrefManager(this);

        RecyclerView recyclerView = findViewById(R.id.rvInterests);
        Button btnNext = findViewById(R.id.btnNext);

        interests = DummyDataProvider.getInterestList();

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new InterestAdapter(interests));

        btnNext.setOnClickListener(v -> saveAndContinue());
    }

    private void saveAndContinue() {
        Set<String> selected = new HashSet<>();
        for (Interest interest : interests) {
            if (interest.isSelected()) {
                selected.add(interest.getName());
            }
        }

        if (selected.isEmpty()) {
            Toast.makeText(this, "Select at least one interest", Toast.LENGTH_SHORT).show();
            return;
        }

        prefManager.saveInterests(selected);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}