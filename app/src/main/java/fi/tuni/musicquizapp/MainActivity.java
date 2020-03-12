package fi.tuni.musicquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private JSONObject token;
    private ConnectTask connectTask;
    private String accessToken;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.play);
        connectTask = new ConnectTask();
        connectTask.execute("token");

        while (true) {
            if (connectTask.getToken() != null) {
                try {
                    token = connectTask.getToken();
                    accessToken = token.getString("access_token");
                    connectTask.cancel(true);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d("WUHUU", accessToken);
    }

    public void clicked(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }
}
