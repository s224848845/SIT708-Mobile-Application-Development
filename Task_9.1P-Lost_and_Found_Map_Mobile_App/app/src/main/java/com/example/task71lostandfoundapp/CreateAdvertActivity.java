package com.example.task71lostandfoundapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import android.app.DatePickerDialog;
import java.util.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Screen used to create a new Lost or Found advert.
 * This activity collects item details, image, date, category, and geo-location.
 */
public class CreateAdvertActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 100;
    private static final int AUTOCOMPLETE_REQUEST_CODE = 200;
    private static final int LOCATION_PERMISSION_REQUEST = 300;

    RadioGroup radioGroupPostType;
    EditText edtName, edtPhone, edtDescription, edtDate, edtLocation;
    Spinner spinnerCategory;
    ImageView imgPreview;
    Button btnChooseImage, btnGetCurrentLocation, btnSave;

    Uri selectedImageUri = null;
    DBHelper dbHelper;
    FusedLocationProviderClient fusedLocationClient;

    double selectedLatitude = 0.0;
    double selectedLongitude = 0.0;
    boolean locationSelected = false;

    String[] categories = {
            "Electronics", "Pets", "Wallets", "Bags", "Documents", "Keys", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_advert);

        dbHelper = new DBHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialise Google Places SDK for autocomplete location search
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        radioGroupPostType = findViewById(R.id.radioGroupPostType);
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtDescription = findViewById(R.id.edtDescription);
        edtDate = findViewById(R.id.edtDate);

        // Opens a calendar picker when the user taps the date field
        edtDate.setFocusable(false);
        edtDate.setClickable(true);
        edtDate.setOnClickListener(v -> showDatePicker());

        // Opens Google Places autocomplete when the user taps the location field.
        edtLocation = findViewById(R.id.edtLocation);

        edtLocation.setOnClickListener(v -> {
            Toast.makeText(this, "Opening location search...", Toast.LENGTH_SHORT).show();
            openPlaceAutocomplete();
        });

        spinnerCategory = findViewById(R.id.spinnerCategory);
        imgPreview = findViewById(R.id.imgPreview);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);
        btnSave = findViewById(R.id.btnSave);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories
        );
        spinnerCategory.setAdapter(adapter);

        btnChooseImage.setOnClickListener(v -> openImagePicker());

        // Opens Google Places autocomplete when the user taps the location text box
        edtLocation.setOnClickListener(v -> openPlaceAutocomplete());

        btnGetCurrentLocation.setOnClickListener(v -> getCurrentLocation());

        btnSave.setOnClickListener(v -> saveAdvert());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Month value starts from 0 in Android, so 1 is added for display.
                    String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    edtDate.setText(selectedDate);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }

    private void openPlaceAutocomplete() {
        List<Place.Field> fields = Arrays.asList(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.ADDRESS,
                Place.Field.LAT_LNG
        );

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY,
                fields
        )// Bias autocomplete results around Australia
                .setCountries(Arrays.asList("AU"))
                .build(this);

        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST
            );
            return;
        }

        // Requests a fresh location instead of relying on an old cached emulator location.
        fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(location -> {
            if (location != null) {
                selectedLatitude = location.getLatitude();
                selectedLongitude = location.getLongitude();
                locationSelected = true;

                edtLocation.setText(selectedLatitude + ", " + selectedLongitude);

                Toast.makeText(this, "Fresh current location selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to get fresh location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Handles selected item image and keeps permission to access it later.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();

            getContentResolver().takePersistableUriPermission(
                    selectedImageUri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
            );

            imgPreview.setImageURI(selectedImageUri);
        }

        // Handles selected Google Places autocomplete result.
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Place place = Autocomplete.getPlaceFromIntent(data);

            edtLocation.setText(place.getAddress());

            if (place.getLatLng() != null) {
                selectedLatitude = place.getLatLng().latitude;
                selectedLongitude = place.getLatLng().longitude;
                locationSelected = true;
            }
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

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty()
                || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!locationSelected) {
            Toast.makeText(this, "Please select a location using autocomplete or current location", Toast.LENGTH_LONG).show();
            return;
        }

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
                selectedLatitude,
                selectedLongitude,
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