package com.example.task71lostandfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvertActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;

    RadioGroup radioGroupPostType;
    EditText edtName, edtPhone, edtDescription, edtDate, edtLocation;
    Spinner spinnerCategory;
    ImageView imgPreview;
    Button btnChooseImage, btnSave;

    Uri selectedImageUri = null;
    DBHelper dbHelper;

    String[] categories = {
            "Electronics", "Pets", "Wallets", "Bags", "Documents", "Keys", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DBHelper(this);

        radioGroupPostType = findViewById(R.id.radioGroupPostType);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtDescription = findViewById(R.id.edtDescription);
        edtDate = findViewById(R.id.edtDate);
        edtLocation = findViewById(R.id.edtLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        imgPreview = findViewById(R.id.imgPreview);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        spinnerCategory.setAdapter(adapter);

        btnChooseImage.setOnClickListener(v -> openImagePicker());

        btnSave.setOnClickListener(v -> saveAdvert());
    }

    // Opens gallery so user can select an image for the advert
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Receives selected image and displays preview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            // Persist permission so image can still load after app restarts
            getContentResolver().takePersistableUriPermission(
                    selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            imgPreview.setImageURI(selectedImageUri);
        }
    }

    private void saveAdvert() {
        String postType = radioGroupPostType.getCheckedRadioButtonId() == R.id.radioLost
                ? "Lost"
                : "Found";

        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String description = edtDescription.getText().toString().trim();
        String date = edtDate.getText().toString().trim();
        String location = edtLocation.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();

        // Basic validation to make sure all required fields are completed
        if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Image upload
        if (selectedImageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String createdAt = new SimpleDateFormat(
                "dd MMM yyyy, hh:mm a",
                Locale.getDefault()
        ).format(new Date());

        Advert advert = new Advert(
                postType,
                name,
                phone,
                description,
                date,
                location,
                category,
                selectedImageUri.toString(),
                createdAt
        );

        boolean inserted = dbHelper.insertAdvert(advert);

        if (inserted) {
            Toast.makeText(this, "Advert saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to save advert", Toast.LENGTH_SHORT).show();
        }
    }
}
