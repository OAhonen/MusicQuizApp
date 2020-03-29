package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity {
    private Spinner countrySpinner;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> countriesList;
    private final String FINLAND = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMxcczTSoGwZ";
    private final String USA = "https://api.spotify.com/v1/playlists/37i9dQZEVXbLRQDuF5jeBp";
    private final String SWEDEN = "https://api.spotify.com/v1/playlists/37i9dQZEVXbLoATJ81JYXz";
    private final String NETHERLANDS = "https://api.spotify.com/v1/playlists/37i9dQZEVXbKCF6dqVpDkS";
    private String selectedCountry;
    private int countryNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        countriesList = new ArrayList<>(Arrays.asList("Sweden", "Finland", "USA", "Netherlands"));
        Collections.sort(countriesList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countriesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner = findViewById(R.id.changeCountryID);
        countrySpinner.setAdapter(arrayAdapter);
        countrySpinner.setSelection(GlobalPrefs.getCountryNumber());
        selectedCountry = (String) countrySpinner.getSelectedItem();
        Log.d("SETTOKEN", GlobalPrefs.getAccessToken());
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = countriesList.get(position);
                countryNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String checkSelectedCountry(String country) {
        String result = "";
        switch (country) {
            case "Finland":
                result = FINLAND;
                break;
            case "USA":
                result = USA;
                break;
            case "Sweden":
                result = SWEDEN;
                break;
            case "Netherlands":
                result = NETHERLANDS;
                break;
        }
        return result;
    }

    public void backToMenuClick(View v) {
        String countryCode = checkSelectedCountry(selectedCountry);
        if (!selectedCountry.equals(GlobalPrefs.getCountry())) {
            GlobalPrefs.setCountry(selectedCountry);
            GlobalPrefs.setCountryCode(countryCode);
            GlobalPrefs.setCountryNumber(countryNumber);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
