package fi.tuni.musicquizapp;

import android.os.AsyncTask;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ConnectTask extends AsyncTask {
    // basicAuth = encoded Spotify client_id + client_secret
    private String basicAuth = "Basic " + "M2UwMzkwMWQ3ZGRlNDZkMmE2MDM2M2MxZWJlOTUwN2Y6NzgyY2Y5ZDQyMjVmNDJkNjhkNjg3NWVlZDE1MDYzNDM=";
    private JSONObject token;

    @Override
    protected Object doInBackground(Object[] objects) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://accounts.spotify.com/api/token").newBuilder();
        String url = urlBuilder.build().toString();

        // Body of the POST-request contains "grant_type", which is set to "client_credentials"
        // See: Client Credentials flow
        // https://developer.spotify.com/documentation/general/guides/authorization-guide/
        RequestBody reqbody = new FormBody.Builder().addEncoded("grant_type", "client_credentials").build();

        Request request = new Request.Builder()
                .header("Authorization", basicAuth)
                .method("POST", reqbody)
                .url(url)
                .build();

        return null;
    }
}
