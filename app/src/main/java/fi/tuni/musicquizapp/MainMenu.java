package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenu extends AppCompatActivity {
    private ArrayList<ArtistTrackPair> top10Songs;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList<ArtistTrackPair>) extras.getSerializable("top10");
            // accessToken = extras.getString("accessToken");
        }
    }

    public void clicked(View v) {
        if (v.getId() == R.id.top10ID) {
            Intent intent = new Intent(this, TopTracks.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        } else if (v.getId() == R.id.playID) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        }
    }
}
