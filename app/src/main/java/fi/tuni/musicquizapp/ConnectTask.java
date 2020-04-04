package fi.tuni.musicquizapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fi.tuni.musicquizapp.preferences.GlobalPrefs;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Connect to Spotify, fetch access token and playlist.
 */
public class ConnectTask extends AsyncTask {
    // basicAuth = encoded Spotify client_id + client_secret
    private String basicAuth = "Basic " + "M2UwMzkwMWQ3ZGRlNDZkMmE2MDM2M2MxZWJlOTUwN2Y6NzgyY2Y5ZDQyMjVmNDJkNjhkNjg3NWVlZDE1MDYzNDM=";
    private JSONObject token;
    private JSONObject playlist;
    private HttpUrl.Builder urlBuilder;
    private String url;
    private RequestBody requestBody;
    private Request request;

    /**
     * Create http-request.
     * @param objects check, if program wants to fetch access token or playlist
     * @return null
     */
    @Override
    protected Object doInBackground(final Object[] objects) {
        OkHttpClient client = new OkHttpClient();

        // If App is trying to get access token.
        if (objects[0] == "token") {
            fetchAccessToken();
        // If app is trying to fetch playlist
        } else {
            fetchPlaylist(objects[0], objects[1]);
        }

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ConnectTask", "Error with response");
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.d("No response", mMessage);
                // call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                try {
                    if (objects[0] == "token") {
                        token = new JSONObject(mMessage);
                        GlobalPrefs.setAccessTokenFetched(System.currentTimeMillis());
                    } else {
                        playlist = new JSONObject(mMessage);
                        Log.d("PLAYLIST", playlist.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return null;
    }

    /**
     * Add correct information to url and http-request so it is able to get access token.
     */
    private void fetchAccessToken() {
        urlBuilder = HttpUrl.parse("https://accounts.spotify.com/api/token").newBuilder();
        url = urlBuilder.build().toString();

        // Body of the POST-request contains "grant_type", which is set to "client_credentials"
        // See: Client Credentials flow
        // https://developer.spotify.com/documentation/general/guides/authorization-guide/
        requestBody = new FormBody.Builder().addEncoded("grant_type", "client_credentials").build();

        request = new Request.Builder()
                .header("Authorization", basicAuth)
                .method("POST", requestBody)
                .url(url)
                .build();
    }

    /**
     * Add correct information to url and http-request so it is able to get playlist.
     * @param accessToken access token
     */
    private void fetchPlaylist(Object accessToken, Object countryCode) {
        urlBuilder = HttpUrl.parse(countryCode.toString()).newBuilder();
        //urlBuilder.addQueryParameter("fields", "tracks.items(track(artists(name)))");
        urlBuilder.addQueryParameter("fields", "tracks.items(track)");
        url = urlBuilder.build().toString();

        request = new Request.Builder()
                .header("Authorization", "Bearer " + accessToken)
                .url(url)
                .build();
    }

    /**
     * Get access token in JSONObject.
     * @return access token
     */
    public JSONObject getToken() {
        return token;
    }

    /**
     * Get playlist in JSONObject.
     * @return playlist
     */
    public JSONObject getPlaylist() {
        return playlist;
    }
}
