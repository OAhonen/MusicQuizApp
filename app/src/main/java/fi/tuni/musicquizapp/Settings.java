package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Spinner countrySpinner;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> countriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        countriesList = new ArrayList<>(Arrays.asList("Finland", "Sweden", "USA"));
        countrySpinner = findViewById(R.id.changeCountryID);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countriesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(arrayAdapter);
    }
}
