package com.example.llm_enhancedlearningassistantapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.llm_enhancedlearningassistantapp.R;
import com.example.llm_enhancedlearningassistantapp.utils.PrefManager;
import com.example.llm_enhancedlearningassistantapp.utils.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etEmail, etConfirmEmail, etPassword, etConfirmPassword, etPhone;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefManager = new PrefManager(this);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etConfirmEmail = findViewById(R.id.etConfirmEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhone = findViewById(R.id.etPhone);
        Button btnCreate = findViewById(R.id.btnCreateAccount);

        btnCreate.setOnClickListener(v -> register());
    }

    private void register() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String confirmEmail = etConfirmEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (!ValidationUtils.isNotEmpty(username) || !ValidationUtils.isValidEmail(email)
                || !ValidationUtils.isValidEmail(confirmEmail)
                || !ValidationUtils.isNotEmpty(password)
                || !ValidationUtils.isNotEmpty(confirmPassword)
                || !ValidationUtils.isNotEmpty(phone)) {
            Toast.makeText(this, "Please fill all fields correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!email.equals(confirmEmail)) {
            Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        prefManager.saveUser(username, email, phone);
        startActivity(new Intent(this, InterestsActivity.class));
        finish();
    }
}