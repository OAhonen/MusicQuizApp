package fi.tuni.musicquizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    private ArrayList<String> top10PreviewUrls;
    private Button buttonSettings, buttonScores, buttonPlay, buttonTop10;

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
            top10PreviewUrls = (ArrayList) extras.getSerializable("top10urls");
            Log.d("MAINMENU", top10PreviewUrls.toString());
        }
        buttonSettings = findViewById(R.id.settingsID);
        buttonPlay = findViewById(R.id.playID);
        buttonTop10 = findViewById(R.id.top10ID);
        buttonScores = findViewById(R.id.scoresID);
    }

    /**
     * Start the game or check current top-10 tracks.
     * @param v view
     */
    public void clicked(View v) {
        if (v.getId() == R.id.top10ID) {
            buttonTop10.setBackgroundResource(R.drawable.button_clicked);
            setButtonBackground(buttonTop10);
            Intent intent = new Intent(this, TopTracks.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        } else if (v.getId() == R.id.playID) {
            buttonPlay.setBackgroundResource(R.drawable.button_clicked);
            setButtonBackground(buttonPlay);
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("top10", top10Songs);
            intent.putExtra("top10urls", top10PreviewUrls);
            startActivity(intent);
        } else if (v.getId() == R.id.settingsID) {
            buttonSettings.setBackgroundResource(R.drawable.button_clicked);
            setButtonBackground(buttonSettings);
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        } else if (v.getId() == R.id.scoresID) {
            buttonScores.setBackgroundResource(R.drawable.button_clicked);
            setButtonBackground(buttonScores);
            Intent intent = new Intent(this, Highscore.class);
            startActivity(intent);
        }
    }

    private void setButtonBackground(final Button b) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b.setBackgroundResource(R.drawable.button_default);
            }
        }, 1000);
    }
}
