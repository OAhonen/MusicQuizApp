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
import fi.tuni.musicquizapp.preferences.GlobalPrefs;

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
    private String mode;
    private String hideArtist = "Hide artist";

    /**
     * Get top-10 tracks from extras.
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mode = GlobalPrefs.getMode();
        textView = findViewById(R.id.questionID);
        b1 = findViewById(R.id.answer1);
        b2 = findViewById(R.id.answer2);
        b3 = findViewById(R.id.answer3);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList) extras.getSerializable("top10");
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
        if (mode.equals(hideArtist)) {
            textView.setText("Track: " + tracks[turn] + "\nWhose track is this?");
        } else {
            textView.setText("Artist: " + tracks[turn] + "\nWhich track is made by this artist?");
        }
    }

    /**
     * Put artists and tracks to their own array.
     */
    private void setCorrectOrder() {
        for (int i = 0; i < top10Songs.size(); i++) {
            if (mode.equals(hideArtist)) {
                artists[i] = top10Songs.get(i).getArtist();
                tracks[i] = top10Songs.get(i).getTrack();
            } else {
                artists[i] = top10Songs.get(i).getTrack();
                tracks[i] = top10Songs.get(i).getArtist();
            }
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
        String correctAnswer = artists[turn];
        String answer2 = artists[r2];
        String answer3 = artists[r3];

        if (r == 1) {
            b1.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b2.setText(answer2);
            b3.setText(answer3);
        } else if (r == 2) {
            b2.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b3.setText(answer3);
        } else if (r == 3) {
            b3.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b2.setText(answer3);
        }
    }

    private boolean checkDoubles(int n, int m) {
        if (top10Songs.get(n).getArtist().equals(top10Songs.get(m).getArtist())) {
            return false;
        }
        return true;
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
            startActivity(intent);
        }
    }
}
