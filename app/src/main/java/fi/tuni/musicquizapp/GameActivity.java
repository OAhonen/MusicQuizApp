package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Game activity.
 */
public class GameActivity extends AppCompatActivity {
    private ArrayList<ArtistTrackPair> top10Songs;
    private TextView textView;
    private String[] artists = new String[10];
    private String[] tracks = new String[10];
    private Button b1;
    private Button b2;
    private Button b3;
    private int round = 0;
    private boolean[] userAnswers = new boolean[10];
    private String accessToken;

    /**
     * Get top-10 tracks from extras.
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = findViewById(R.id.questionID);
        b1 = findViewById(R.id.answer1);
        b2 = findViewById(R.id.answer2);
        b3 = findViewById(R.id.answer3);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList) extras.getSerializable("top10");
            accessToken = extras.getString("accessToken");
        }
        setCorrectOrder();
        setUpQuestion(round);
        setButtons(round);
    }

    /**
     * Create question to the user.
     * @param turn current turn
     */
    private void setUpQuestion(int turn) {
        textView.setText("Track: " + tracks[turn] + "\nWhose track is this?");
    }

    /**
     * Put artists and tracks to their own array.
     */
    private void setCorrectOrder() {
        for (int i = 0; i < top10Songs.size(); i++) {
            artists[i] = top10Songs.get(i).getArtist();
            tracks[i] = top10Songs.get(i).getTrack();
        }
    }

    /**
     * Put randomly the correct answer to one of the buttons.
     * Check that all the button's have different answer.
     * @param turn current turn
     */
    private void setButtons(int turn) {
        Random random = new Random();
        int r = random.nextInt(4-1) + 1;
        int r2 = random.nextInt(9);
        int r3 = random.nextInt(9);
        String answer1 = artists[turn];
        String answer2 = artists[r2];
        String answer3 = artists[r3];

        if (r == 1) {
            b1.setText(answer1);
            while (answer1.equals(answer2) || answer1.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                answer2 = artists[r2];
                r3 = random.nextInt(9);
                answer3 = artists[r3];
            }
            b2.setText(answer2);
            b3.setText(answer3);
        } else if (r == 2) {
            b2.setText(answer1);
            while (answer1.equals(answer2) || answer1.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                answer2 = artists[r2];
                r3 = random.nextInt(9);
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b3.setText(answer3);
        } else if (r == 3) {
            b3.setText(answer1);
            while (answer1.equals(answer2) || answer1.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                answer2 = artists[r2];
                r3 = random.nextInt(9);
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b2.setText(answer3);
        }
    }

    /**
     * Check user's answer and put it to array. Also check if game is over.
     * @param v view
     */
    public void answered(View v) {
        if (v.getId() == R.id.answer1) {
            if (b1.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        } else if (v.getId() == R.id.answer2) {
            if (b2.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        } else if (v.getId() == R.id.answer3) {
            if (b3.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        }

        if (round < 9) {
            round++;
            setUpQuestion(round);
            setButtons(round);
        } else {
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("userAnswers", userAnswers);
            intent.putExtra("top10", top10Songs);
            intent.putExtra("accessToken", accessToken);
            startActivity(intent);
        }
    }
}
