package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private HashMap<String, String> top10Songs;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (HashMap) extras.getSerializable("top10");
            accessToken = extras.getString("accessToken");
        }
        Log.d("SONGS from MAINMENU", top10Songs.toString());
    }
}
