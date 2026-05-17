package com.example.llm_enhancedlearningassistantapp.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;

public class UpgradeActivity extends AppCompatActivity {

    private PrefManager prefManager;
    private TextView tvCurrentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        prefManager = new PrefManager(this);

        ImageButton btnBack = findViewById(R.id.btnBack);
        tvCurrentPlan = findViewById(R.id.tvCurrentPlan);

        Button btnStarter = findViewById(R.id.btnStarter);
        Button btnIntermediate = findViewById(R.id.btnIntermediate);
        Button btnAdvanced = findViewById(R.id.btnAdvanced);

        tvCurrentPlan.setText("Current Plan: " + prefManager.getAccountPlan());

        btnBack.setOnClickListener(v -> finish());

        btnStarter.setOnClickListener(v -> showPurchaseSheet("Starter"));
        btnIntermediate.setOnClickListener(v -> showPurchaseSheet("Intermediate"));
        btnAdvanced.setOnClickListener(v -> showPurchaseSheet("Advanced"));
    }


    private void showPurchaseSheet(String plan) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_purchase);

        TextView tvDialogPlan = dialog.findViewById(R.id.tvDialogPlan);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmPurchase);
        Button btnCancel = dialog.findViewById(R.id.btnCancelPurchase);

        tvDialogPlan.setText(plan + " Plan");

        btnConfirm.setOnClickListener(v -> {
            prefManager.saveAccountPlan(plan);
            tvCurrentPlan.setText("Current Plan: " + plan);
            Toast.makeText(this, plan + " activated successfully", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();

        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setGravity(Gravity.BOTTOM);
            window.setDimAmount(0.55f);
            window.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
            );
        }
    }
}