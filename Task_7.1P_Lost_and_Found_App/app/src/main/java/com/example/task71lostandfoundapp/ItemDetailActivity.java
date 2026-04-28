package com.example.task71lostandfoundapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {

    ImageView imgDetail;
    TextView txtPostType, txtName, txtPhone, txtDescription, txtDate,
            txtLocation, txtCategory, txtCreatedAt;
    Button btnRemove;

    DBHelper dbHelper;
    int advertId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        dbHelper = new DBHelper(this);

        imgDetail = findViewById(R.id.imgDetail);
        txtPostType = findViewById(R.id.txtPostType);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtDescription = findViewById(R.id.txtDescription);
        txtDate = findViewById(R.id.txtDate);
        txtLocation = findViewById(R.id.txtLocation);
        txtCategory = findViewById(R.id.txtCategory);
        txtCreatedAt = findViewById(R.id.txtCreatedAt);
        btnRemove = findViewById(R.id.btnRemove);

        advertId = getIntent().getIntExtra("advert_id", -1);

        if (advertId != -1) {
            loadAdvertDetails();
        }

        btnRemove.setOnClickListener(v -> removeAdvert());
    }

    private void loadAdvertDetails() {
        Advert advert = dbHelper.getAdvertById(advertId);

        if (advert == null) {
            Toast.makeText(this, "Advert not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        imgDetail.setImageURI(Uri.parse(advert.getImageUri()));
        txtPostType.setText("Post Type: " + advert.getPostType());
        txtName.setText("Name: " + advert.getName());
        txtPhone.setText("Phone: " + advert.getPhone());
        txtDescription.setText("Description: " + advert.getDescription());
        txtDate.setText("Date: " + advert.getDateText());
        txtLocation.setText("Location: " + advert.getLocation());
        txtCategory.setText("Category: " + advert.getCategory());
        txtCreatedAt.setText("Posted: " + advert.getCreatedAt());
    }

    // Removes advert after owner has been found
    private void removeAdvert() {
        boolean deleted = dbHelper.deleteAdvert(advertId);

        if (deleted) {
            Toast.makeText(this, "Advert removed", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to remove advert", Toast.LENGTH_SHORT).show();
        }
    }
}
