package fi.tuni.musicquizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private boolean[] userAnswers;
    private TextView textView;
    private Button mainMenu;
    private Button playAgain;
    private HashMap<String, String> top10Songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userAnswers = extras.getBooleanArray("userAnswers");
            top10Songs = (HashMap) extras.getSerializable("top10");
        }
        textView = findViewById(R.id.user_result);
        mainMenu = findViewById(R.id.toMenuID);
        playAgain = findViewById(R.id.playAgainID);
        calcResult();
    }

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
