package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Main Menu.
 */
public class MainMenu extends AppCompatActivity {
    private ArrayList<ArtistTrackPair> top10Songs;
    private String accessToken;

    /**
     * When MainMenu is called, it gets top-10 tracks from extras.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList<ArtistTrackPair>) extras.getSerializable("top10");
            accessToken = extras.getString("accessToken");
        }
    }

    /**
     * Start the game or check current top-10 tracks.
     * @param v view
     */
    public void clicked(View v) {
        if (v.getId() == R.id.top10ID) {
            Intent intent = new Intent(this, TopTracks.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        } else if (v.getId() == R.id.playID) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        } else if (v.getId() == R.id.settingsID) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("accessToken", accessToken);
            startActivity(intent);
        }
    }
}
