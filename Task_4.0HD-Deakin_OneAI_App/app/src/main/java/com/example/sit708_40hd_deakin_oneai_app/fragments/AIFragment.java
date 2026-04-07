package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;
import com.example.sit708_40hd_deakin_oneai_app.ai.AIEngine;

/**
 * AIFragment handles UI only.
 * Logic is delegated to AIEngine.
 */
public class AIFragment extends Fragment {

    private AIEngine aiEngine;

    public AIFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ai, container, false);

        EditText inputText = view.findViewById(R.id.inputText);
        Button askButton = view.findViewById(R.id.askButton);
        TextView responseText = view.findViewById(R.id.responseText);

        aiEngine = new AIEngine();

        askButton.setOnClickListener(v -> {

            String userInput = inputText.getText().toString().trim();

            if (TextUtils.isEmpty(userInput)) {
                responseText.setText("Please enter a question.");
                return;
            }

            // Call AIEngine
            String result = aiEngine.generateResponse(userInput);

            responseText.setText(result);
        });

        return view;
    }
}