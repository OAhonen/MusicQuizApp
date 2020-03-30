package fi.tuni.musicquizapp;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.GlobalPrefs;
import fi.tuni.musicquizapp.preferences.HighscorePrefs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Fetches top tracks from Spotify and moves to MainMenu.
 */
public class MainActivity extends AppCompatActivity {
    private JSONObject token;
    private JSONObject playlist;
    private ConnectTask connectTask;
    private String accessToken;
    private ArrayList<ArtistTrackPair> top10Songs;
    private long timeElapsed;

    /**
     * Creates necessary variables and calls different methods.
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        top10Songs = new ArrayList<>();
        GlobalPrefs.init(this);
        HighscorePrefs.init(this);
        // Log.d("MAINTOKEN", GlobalPrefs.getAccessToken());
        timeElapsed = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - GlobalPrefs.getAccessTokenFetched());
        // If user has used the app recently (max 1 hour ago), only fetch playlist.
        // Else fetch new access token.
        if (timeElapsed < 60) {
            checkPlaylist();
            getTop10();
            goToMainMenu();
        } else {
            connectTask = new ConnectTask();
            connectTask.execute("token");
            getAccessToken();
            checkPlaylist();
            getTop10();
            goToMainMenu();
        }
    }

    /**
     * Fetch access token from Spotify.
     */
    private void getAccessToken() {
        while (true) {
            if (connectTask.getToken() != null) {
                try {
                    token = connectTask.getToken();
                    accessToken = token.getString("access_token");
                    GlobalPrefs.setAccessToken(accessToken);
                    connectTask.cancel(true);
                    break;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Fetch playlist from Spotify.
     */
    private void checkPlaylist() {
        connectTask = new ConnectTask();
        connectTask.execute(GlobalPrefs.getAccessToken(), GlobalPrefs.getCountryCode());
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

    /**
     * Get top-10 tracks from the playlist and add them to arraylist.
     */
    private void getTop10() {
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
                top10Songs.add(new ArtistTrackPair(artist, song));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * After fetching tracks and putting them to arraylist, go to mainmenu.
     */
    public void goToMainMenu() {
        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra("top10", top10Songs);
        startActivity(intent);
    }
}
