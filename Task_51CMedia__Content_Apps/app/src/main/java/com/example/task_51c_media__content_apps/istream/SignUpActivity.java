package com.example.task_51c_media__content_apps.istream;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.task_51c_media__content_apps.R;
import com.example.task_51c_media__content_apps.istream.data.AppDatabase;
import com.example.task_51c_media__content_apps.istream.model.User;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnCreateAccount;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsernameSignUp);
        etPassword = findViewById(R.id.etPasswordSignUp);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        db = AppDatabase.getInstance(this);

        btnCreateAccount.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String confirmPassword = etConfirmPassword.getText().toString().trim();

            if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.userDao().getUserByUsername(username) != null) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            db.userDao().insert(new User(fullName, username, password));
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}