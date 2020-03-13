package fi.tuni.musicquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private JSONObject token;
    private JSONObject playlist;
    private ConnectTask connectTask;
    private String accessToken;
    private TextView textLoading;
    private ArrayList<String> top10;
    private Map<String, String> top10Songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        checkPlaylist();
        getTop10();

        Log.d("AccessToken", accessToken);
    }

    private void checkPlaylist() {
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
    }

    private void getTop10() {
        top10Songs = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            try {
                String artist = String.valueOf(playlist.getJSONObject("tracks")
                        .getJSONArray("items")
                        .getJSONObject(i)
                        .getJSONObject("track")
                        .getJSONArray("artists")
                        .getJSONObject(0).get("name"));
                String song = String.valueOf(playlist.getJSONObject("tracks")
                        .getJSONArray("items")
                        .getJSONObject(i)
                        .getJSONObject("track")
                        .getString("name"));
                Log.d("ARTIST + SONG", artist + " " + song);
                top10Songs.put(artist, song);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("ALLSONGS", top10Songs.toString());
    }

    public void clicked(View v) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("accessToken", accessToken);
        startActivity(intent);
    }
}
