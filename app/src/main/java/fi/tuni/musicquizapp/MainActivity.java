package fi.tuni.musicquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private JSONObject token;
    private ConnectTask connectTask;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectTask = new ConnectTask();
        connectTask.execute();

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
}
