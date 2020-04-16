package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.GlobalPrefs;

/**
 * Settings class.
 */
public class Settings extends AppCompatActivity {
    private Spinner countrySpinner;
    private ArrayAdapter<String> arrayAdapterCountries;
    private ArrayList<String> countriesList;
    private ArrayAdapter<String> arrayAdapterMode;
    private Spinner modeSpinner;
    private String selectedMode;
    private int modeNumber = 0;
    private String[] modeArray = {"Hide artist", "Hide track"};
    private final String FINLAND = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMxcczTSoGwZ";
    private final String USA = "https://api.spotify.com/v1/playlists/37i9dQZEVXbLRQDuF5jeBp";
    private final String SWEDEN = "https://api.spotify.com/v1/playlists/37i9dQZEVXbLoATJ81JYXz";
    private final String NETHERLANDS = "https://api.spotify.com/v1/playlists/37i9dQZEVXbKCF6dqVpDkS";
    private String selectedCountry;
    private int countryNumber = 0;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        saveButton = findViewById(R.id.backToMenuButton);
        setMode();
        setCountries();
    }

    private void setCountries() {
        countriesList = new ArrayList<>(Arrays.asList("Sweden", "Finland", "USA", "Netherlands"));
        Collections.sort(countriesList);
        arrayAdapterCountries = new ArrayAdapter<String>(this, R.layout.adapter_settings, countriesList);
        arrayAdapterCountries.setDropDownViewResource(R.layout.adapter_settings);
        countrySpinner = findViewById(R.id.changeCountryID);
        countrySpinner.setAdapter(arrayAdapterCountries);
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

    private void setMode() {
        arrayAdapterMode = new ArrayAdapter<String>(this, R.layout.adapter_settings, modeArray);
        arrayAdapterMode.setDropDownViewResource(R.layout.adapter_settings);
        modeSpinner = findViewById(R.id.changeMode);
        modeSpinner.setAdapter(arrayAdapterMode);
        modeSpinner.setSelection(GlobalPrefs.getModeNumber());
        selectedMode = (String) modeSpinner.getSelectedItem();
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMode = modeArray[position];
                modeNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Set country's http-address.
     * @param country country
     * @return http-address
     */
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

    /**
     * Back to menu and set country, if it has changed.
     * @param v view
     */
    public void backToMenuClick(View v) {
        saveButton.setBackgroundResource(R.drawable.button_clicked);
        String countryCode = checkSelectedCountry(selectedCountry);
        if (!selectedCountry.equals(GlobalPrefs.getCountry())) {
            GlobalPrefs.setCountry(selectedCountry);
            GlobalPrefs.setCountryCode(countryCode);
            GlobalPrefs.setCountryNumber(countryNumber);
        }
        if (!selectedMode.equals(GlobalPrefs.getMode())) {
            GlobalPrefs.setMode(selectedMode);
            GlobalPrefs.setModeNumber(modeNumber);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
