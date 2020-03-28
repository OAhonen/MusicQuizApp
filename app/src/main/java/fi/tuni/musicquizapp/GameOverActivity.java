package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Game over activity.
 */
public class GameOverActivity extends AppCompatActivity {
    private boolean[] userAnswers;
    private TextView textView;
    private Button mainMenu;
    private Button playAgain;
    private ArrayList<ArtistTrackPair> top10Songs;
    private String accessToken;

    /**
     * Get top-10 tracks and user's answers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        Log.d("GOVERTOKEN", GlobalPrefs.getAccessToken());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userAnswers = extras.getBooleanArray("userAnswers");
            top10Songs = (ArrayList<ArtistTrackPair>) extras.getSerializable("top10");
        }
        textView = findViewById(R.id.user_result);
        mainMenu = findViewById(R.id.toMenuID);
        playAgain = findViewById(R.id.playAgainID);
        calcResult();
    }

    /**
     * Check user's answer percentage.
     */
    private void calcResult() {
        int correctAnswers = 0;
        for (boolean userAnswer : userAnswers) {
            if (userAnswer) {
                correctAnswers++;
            }
        }
        double percentage = (double) correctAnswers * 10;
        textView.setText("Nice. You got " + percentage + "% correct answers.");
    }

    /**
     * Go back to mainmenu or start new game.
     * @param v view
     */
    public void gameoverClicked(View v) {
        if (v.getId() == R.id.toMenuID) {
            Intent intent = new Intent(this, MainMenu.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        } else if (v.getId() == R.id.playAgainID) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("top10", top10Songs);
            startActivity(intent);
        }
    }
}
