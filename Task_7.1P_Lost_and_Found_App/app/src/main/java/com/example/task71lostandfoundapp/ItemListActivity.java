package com.example.task71lostandfoundapp;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ItemListActivity extends AppCompatActivity {

    Spinner spinnerFilter;
    ListView listViewItems;

    DBHelper dbHelper;
    AdvertAdapter adapter;
    ArrayList<Advert> advertList;

    String[] filterCategories = {
            "All", "Electronics", "Pets", "Wallets", "Bags", "Documents", "Keys", "Other"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        spinnerFilter = findViewById(R.id.spinnerFilter);
        listViewItems = findViewById(R.id.listViewItems);
        dbHelper = new DBHelper(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                filterCategories
        );
        spinnerFilter.setAdapter(spinnerAdapter);

        loadAllItems();

        // Filters adverts by selected category
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                String selectedCategory = filterCategories[position];

                if (selectedCategory.equals("All")) {
                    loadAllItems();
                } else {
                    loadItemsByCategory(selectedCategory);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllItems();
    }

    private void loadAllItems() {
        advertList = dbHelper.getAllAdverts();
        adapter = new AdvertAdapter(this, advertList);
        listViewItems.setAdapter(adapter);
    }

    private void loadItemsByCategory(String category) {
        advertList = dbHelper.getAdvertsByCategory(category);
        adapter = new AdvertAdapter(this, advertList);
        listViewItems.setAdapter(adapter);
    }
}
