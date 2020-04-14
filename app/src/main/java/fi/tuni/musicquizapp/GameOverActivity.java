package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.GlobalPrefs;
import fi.tuni.musicquizapp.preferences.HighscorePrefs;

/**
 * Game over activity.
 */
public class GameOverActivity extends AppCompatActivity {
    private boolean[] userAnswers;
    private TextView textViewResult;
    private ArrayList<ArtistTrackPair> top10Songs;
    private ArrayList<String> top10PreviewUrls;
    private int correctAnswers = 0;
    private TextView textViewMadeIt;
    private EditText editTextName;
    private Button saveScore;
    private int scorePosition = 0;

    /**
     * Get top-10 tracks and user's answers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userAnswers = extras.getBooleanArray("userAnswers");
            top10Songs = (ArrayList<ArtistTrackPair>) extras.getSerializable("top10");
            top10PreviewUrls = (ArrayList) extras.getSerializable("top10urls");
        }
        textViewResult = findViewById(R.id.userResult);
        textViewMadeIt = findViewById(R.id.madeItToScores);
        editTextName = findViewById(R.id.editName);
        saveScore = findViewById(R.id.saveScore);
        calcResult();
        checkHighScore();
    }

    /**
     * Check user's answer percentage.
     */
    private void calcResult() {
        for (boolean userAnswer : userAnswers) {
            if (userAnswer) {
                correctAnswers++;
            }
        }
        double percentage = (double) correctAnswers * 10;
        textViewResult.setText("Nice. You got " + percentage + "% correct answers.");
    }

    /**
     * Check, if user made it to the high scores, set position in high scores and show
     * the form to save score.
     */
    private void checkHighScore() {
        if (correctAnswers >= HighscorePrefs.getScore3()) {
            if (correctAnswers >= HighscorePrefs.getScore1()) {
                scorePosition = 1;
            } else if (correctAnswers >= HighscorePrefs.getScore2()) {
                scorePosition = 2;
            } else if (correctAnswers >= HighscorePrefs.getScore3()) {
                scorePosition = 3;
            }
            setScoreVisible(View.VISIBLE);
        }
    }

    /**
     * Show or hide the high score form.
     * @param visibility visibility of the form
     */
    private void setScoreVisible(int visibility) {
        textViewMadeIt.setVisibility(visibility);
        editTextName.setVisibility(visibility);
        saveScore.setVisibility(visibility);
        textViewMadeIt.setText("You made it to the " + scorePosition + ". place!");
    }

    /**
     * Save high score to prefs.
     * @param v view
     */
    public void saveToScores(View v) {
        String name = editTextName.getText().toString();
        if (name.length() < 16 && name.length() > 0) {
            if (scorePosition == 1) {
                HighscorePrefs.setName3(HighscorePrefs.getName2());
                HighscorePrefs.setCountry3(HighscorePrefs.getCountry2());
                HighscorePrefs.setScore3(HighscorePrefs.getScore2());
                HighscorePrefs.setName2(HighscorePrefs.getName1());
                HighscorePrefs.setCountry2(HighscorePrefs.getCountry1());
                HighscorePrefs.setScore2(HighscorePrefs.getScore1());
                HighscorePrefs.setName1(name);
                HighscorePrefs.setCountry1(GlobalPrefs.getCountry());
                HighscorePrefs.setScore1(correctAnswers);
            } else if (scorePosition == 2) {
                HighscorePrefs.setName3(HighscorePrefs.getName2());
                HighscorePrefs.setCountry3(HighscorePrefs.getCountry2());
                HighscorePrefs.setScore3(HighscorePrefs.getScore2());
                HighscorePrefs.setName2(name);
                HighscorePrefs.setCountry2(GlobalPrefs.getCountry());
                HighscorePrefs.setScore2(correctAnswers);
            } else if (scorePosition == 3) {
                HighscorePrefs.setName3(name);
                HighscorePrefs.setCountry3(GlobalPrefs.getCountry());
                HighscorePrefs.setScore3(correctAnswers);
            }
            setScoreVisible(View.INVISIBLE);
        }
    }

    /**
     * Go back to mainmenu or start new game.
     * @param v view
     */
    public void gameoverClicked(View v) {
        if (v.getId() == R.id.toMenuID) {
            Intent intent = new Intent(this, MainMenu.class);
            intent.putExtra("top10", top10Songs);
            intent.putExtra("top10urls", top10PreviewUrls);
            startActivity(intent);
        } else if (v.getId() == R.id.playAgainID) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("top10", top10Songs);
            intent.putExtra("top10urls", top10PreviewUrls);
            startActivity(intent);
        }
    }
}
