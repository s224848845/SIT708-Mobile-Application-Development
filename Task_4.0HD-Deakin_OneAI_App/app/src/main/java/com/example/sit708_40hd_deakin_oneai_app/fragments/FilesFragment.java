package com.example.sit708_40hd_deakin_oneai_app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sit708_40hd_deakin_oneai_app.R;

/**
 * FilesFragment is the local academic storage area.
 * This is a simple starter version for Task 4.0.
 */
public class FilesFragment extends Fragment {

    public FilesFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_files, container, false);

        Button btnUploadFile = view.findViewById(R.id.btnUploadFile);
        TextView txtFilesList = view.findViewById(R.id.txtFilesList);

        btnUploadFile.setOnClickListener(v -> {
            Toast.makeText(getContext(), "File upload feature will be added next.", Toast.LENGTH_SHORT).show();
            txtFilesList.setText("Sample files:\n• SIT708_Proposal.pdf\n• Week_3_Notes.docx\n• Java_Code_Snippets.txt");
        });

        return view;
    }
}