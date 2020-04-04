package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.HighscorePrefs;

/**
 * Show high scores.
 */
public class Highscore extends AppCompatActivity {
    private TextView n1, n2, n3, s1, s2, s3, c1, c2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        setupTable();
        updateTable();
    }

    /**
     * Find textviews.
     */
    private void setupTable() {
        n1 = findViewById(R.id.colNameFirst);
        n2 = findViewById(R.id.colNameSecond);
        n3 = findViewById(R.id.colNameThird);
        s1 = findViewById(R.id.colScoreFirst);
        s2 = findViewById(R.id.colScoreSecond);
        s3 = findViewById(R.id.colScoreThird);
        c1 = findViewById(R.id.colPlaylistFirst);
        c2 = findViewById(R.id.colPlaylistSecond);
        c3 = findViewById(R.id.colPlaylistThird);
    }

    /**
     * Set textviews.
     */
    private void updateTable() {
        n1.setText(HighscorePrefs.getName1());
        s1.setText(String.valueOf(HighscorePrefs.getScore1()));
        c1.setText(HighscorePrefs.getCountry1());
        n2.setText(HighscorePrefs.getName2());
        s2.setText(String.valueOf(HighscorePrefs.getScore2()));
        c2.setText(HighscorePrefs.getCountry2());
        n3.setText(HighscorePrefs.getName3());
        s3.setText(String.valueOf(HighscorePrefs.getScore3()));
        c3.setText(HighscorePrefs.getCountry3());
    }
}
