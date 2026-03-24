package com.example.sit708_21p_travelapp;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

// Main activity class for the Travel Companion App
public class MainActivity extends AppCompatActivity {

    // UI components used in the activity
    Spinner categorySpinner, fromSpinner, toSpinner;
    EditText inputValue;
    Button convertButton, resetButton;
    TextView resultText, resultLabel;
    CardView resultCard;

    // Available conversion categories
    String[] categories = {"Currency", "Fuel", "Temperature"};

    // Units for each category
    String[] currencyUnits = {"USD", "AUD", "EUR", "JPY", "GBP"};
    String[] fuelUnits = {"mpg", "km/L", "gallon", "liter", "nautical mile", "kilometer"};
    String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enables edge-to-edge display for a modern full-screen layout
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Applies padding dynamically so content does not overlap with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Link Java variables with XML UI components
        categorySpinner = findViewById(R.id.categorySpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resetButton = findViewById(R.id.resetButton);
        resultText = findViewById(R.id.resultText);
        resultLabel = findViewById(R.id.resultLabel);
        resultCard = findViewById(R.id.resultCard);

        // Adapter for category spinner
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);

        // Set default UI state when app starts
        updateUnitSpinners("Currency");
        updateResultCardColor("Currency");
        updateResultLabel("Currency");

        // Handle category selection changes
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];

                // Update units and result UI based on selected category
                updateUnitSpinners(selectedCategory);
                updateResultCardColor(selectedCategory);
                updateResultLabel(selectedCategory);

                // Reset result display when category changes
                resultText.setText("Result will appear here");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            } // No action needed
        });

        // Convert button triggers conversion process
        convertButton.setOnClickListener(v -> performConversion());

        // Reset button clears input and restores default state
        resetButton.setOnClickListener(v -> {
            inputValue.setText("");
            categorySpinner.setSelection(0);
            updateUnitSpinners("Currency");
            updateResultCardColor("Currency");
            updateResultLabel("Currency");
            resultText.setText("Result will appear here");
        });
    }

    // Updates source and destination spinners depending on selected category
    private void updateUnitSpinners(String category) {
        String[] selectedUnits;

        switch (category) {
            case "Currency":
                selectedUnits = currencyUnits;
                break;
            case "Fuel":
                selectedUnits = fuelUnits;
                break;
            case "Temperature":
                selectedUnits = temperatureUnits;
                break;
            default:
                selectedUnits = new String[]{};
                break;
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                selectedUnits
        );
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(unitAdapter);
        toSpinner.setAdapter(unitAdapter);

        // Set a different default destination unit for convenience
        if (selectedUnits.length > 1) {
            toSpinner.setSelection(1);
        }
    }

    // Changes result card color based on selected category for better visual feedback
    private void updateResultCardColor(String category) {
        if (category.equals("Currency")) {
            resultCard.setCardBackgroundColor(Color.parseColor("#DBEAFE"));
        } else if (category.equals("Fuel")) {
            resultCard.setCardBackgroundColor(Color.parseColor("#DCFCE7"));
        } else if (category.equals("Temperature")) {
            resultCard.setCardBackgroundColor(Color.parseColor("#FEE2E2"));
        } else {
            resultCard.setCardBackgroundColor(Color.parseColor("#E2E8F0"));
        }
    }

    // Updates result section heading according to selected category
    private void updateResultLabel(String category) {
        if (category.equals("Currency")) {
            resultLabel.setText("Currency Result");
        } else if (category.equals("Fuel")) {
            resultLabel.setText("Fuel Result");
        } else if (category.equals("Temperature")) {
            resultLabel.setText("Temperature Result");
        } else {
            resultLabel.setText("Conversion Result");
        }
    }

    // Adds a simple fade-in animation when showing a result
    private void animateResult() {
        resultText.setAlpha(0f);
        resultText.animate()
                .alpha(1f)
                .setDuration(400)
                .start();
    }

    // Validates user input and performs selected conversion
    private void performConversion() {
        String category = categorySpinner.getSelectedItem().toString();
        String fromUnit = fromSpinner.getSelectedItem().toString();
        String toUnit = toSpinner.getSelectedItem().toString();
        String inputText = inputValue.getText().toString().trim();

        // Check for empty input
        if (inputText.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_LONG).show();
            return;
        }

        double value;
        try {
            // Convert input string into numeric value
            value = Double.parseDouble(inputText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show();
            return;
        }

        // Handle same-unit conversion without extra calculation
        if (fromUnit.equals(toUnit)) {
            resultText.setText(String.format(Locale.getDefault(),
                    "%.2f %s = %.2f %s", value, fromUnit, value, toUnit));
            animateResult();
            Toast.makeText(this, "Source and destination are the same", Toast.LENGTH_LONG).show();
            return;
        }

        // Validation for invalid negative values
        if (category.equals("Currency") && value < 0) {
            Toast.makeText(this, "Currency value cannot be negative", Toast.LENGTH_LONG).show();
            return;
        }

        if (category.equals("Fuel") && value < 0) {
            Toast.makeText(this, "Fuel value cannot be negative", Toast.LENGTH_LONG).show();
            return;
        }

        if (category.equals("Temperature") && fromUnit.equals("Kelvin") && value < 0) {
            Toast.makeText(this, "Kelvin cannot be negative", Toast.LENGTH_LONG).show();
            return;
        }

        Double result = null;

        // Call the correct conversion method based on category
        switch (category) {
            case "Currency":
                result = convertCurrency(fromUnit, toUnit, value);
                break;
            case "Fuel":
                result = convertFuel(fromUnit, toUnit, value);
                break;
            case "Temperature":
                result = convertTemperature(fromUnit, toUnit, value);
                break;
        }

        // Display result if conversion is successful
        if (result != null) {
            resultText.setText(String.format(Locale.getDefault(),
                    "%.2f %s = %.2f %s", value, fromUnit, result, toUnit));
            animateResult();
        } else {
            Toast.makeText(this, "Unsupported conversion", Toast.LENGTH_LONG).show();
        }
    }

    // Currency conversion using USD as the base reference unit
    private Double convertCurrency(String fromUnit, String toUnit, double value) {
        double usdValue;

        switch (fromUnit) {
            case "USD":
                usdValue = value;
                break;
            case "AUD":
                usdValue = value / 1.55;
                break;
            case "EUR":
                usdValue = value / 0.92;
                break;
            case "JPY":
                usdValue = value / 148.50;
                break;
            case "GBP":
                usdValue = value / 0.78;
                break;
            default:
                return null;
        }

        switch (toUnit) {
            case "USD":
                return usdValue;
            case "AUD":
                return usdValue * 1.55;
            case "EUR":
                return usdValue * 0.92;
            case "JPY":
                return usdValue * 148.50;
            case "GBP":
                return usdValue * 0.78;
            default:
                return null;
        }
    }

    // Fuel and travel-related unit conversions
    private Double convertFuel(String fromUnit, String toUnit, double value) {
        if (fromUnit.equals("mpg") && toUnit.equals("km/L")) {
            return value * 0.425;
        } else if (fromUnit.equals("km/L") && toUnit.equals("mpg")) {
            return value / 0.425;
        } else if (fromUnit.equals("gallon") && toUnit.equals("liter")) {
            return value * 3.785;
        } else if (fromUnit.equals("liter") && toUnit.equals("gallon")) {
            return value / 3.785;
        } else if (fromUnit.equals("nautical mile") && toUnit.equals("kilometer")) {
            return value * 1.852;
        } else if (fromUnit.equals("kilometer") && toUnit.equals("nautical mile")) {
            return value / 1.852;
        } else {
            return null;
        }
    }

    // Temperature conversion formulas
    private Double convertTemperature(String fromUnit, String toUnit, double value) {
        if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
            return (value * 1.8) + 32;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
            return (value - 32) / 1.8;
        } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
            return value + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
            return value - 273.15;
        } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
            return ((value - 32) / 1.8) + 273.15;
        } else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
            return ((value - 273.15) * 1.8) + 32;
        } else {
            return null;
        }
    }
}