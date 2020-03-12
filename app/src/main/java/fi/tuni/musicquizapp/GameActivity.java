package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private JSONObject playlist;
    private ConnectTask connectTask;
    private String accessToken;
    private String playlist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accessToken = getIntent().getExtras().getString("accessToken");
        connectTask = new ConnectTask();
        connectTask.execute(accessToken);
        while (true) {
            if (connectTask.getPlaylist() != null) {
                try {
                    playlist = connectTask.getPlaylist();
                    connectTask.cancel(true);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d("WUHUU", accessToken);
    }
}
