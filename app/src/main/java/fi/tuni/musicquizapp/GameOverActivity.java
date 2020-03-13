package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    private boolean[] userAnswers;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userAnswers = extras.getBooleanArray("userAnswers");
        }
        textView = findViewById(R.id.user_result);
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
}
