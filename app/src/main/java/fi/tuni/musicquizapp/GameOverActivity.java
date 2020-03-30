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

    private void checkHighScore() {
        if (correctAnswers >= HighscorePrefs.getScore1()) {
            scorePosition = 1;
            HighscorePrefs.setName3(HighscorePrefs.getName2());
            HighscorePrefs.setCountry3(HighscorePrefs.getCountry2());
            HighscorePrefs.setScore3(HighscorePrefs.getScore2());
            HighscorePrefs.setName2(HighscorePrefs.getName1());
            HighscorePrefs.setCountry2(HighscorePrefs.getCountry1());
            HighscorePrefs.setScore2(HighscorePrefs.getScore1());
            setVisible(View.VISIBLE);
        } else if (correctAnswers >= HighscorePrefs.getScore2()) {
            scorePosition = 2;
            HighscorePrefs.setName3(HighscorePrefs.getName2());
            HighscorePrefs.setCountry3(HighscorePrefs.getCountry2());
            HighscorePrefs.setScore3(HighscorePrefs.getScore2());
            setVisible(View.VISIBLE);
        } else if (correctAnswers >= HighscorePrefs.getScore3()) {
            scorePosition = 3;
            setVisible(View.VISIBLE);
        }
    }

    private void setVisible(int visibility) {
        textViewMadeIt.setVisibility(visibility);
        editTextName.setVisibility(visibility);
        saveScore.setVisibility(visibility);
        textViewMadeIt.setText("You made it to the " + scorePosition + ". place!");
    }

    public void saveToScores(View v) {
        if (scorePosition == 1) {
            HighscorePrefs.setName1(editTextName.getText().toString());
            HighscorePrefs.setCountry1(GlobalPrefs.getCountry());
            HighscorePrefs.setScore1(correctAnswers);
        } else if (scorePosition == 2) {
            HighscorePrefs.setName2(editTextName.getText().toString());
            HighscorePrefs.setCountry2(GlobalPrefs.getCountry());
            HighscorePrefs.setScore2(correctAnswers);
        } else if (scorePosition == 3) {
            HighscorePrefs.setName3(editTextName.getText().toString());
            HighscorePrefs.setCountry3(GlobalPrefs.getCountry());
            HighscorePrefs.setScore3(correctAnswers);
        }
        setVisible(View.INVISIBLE);
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
