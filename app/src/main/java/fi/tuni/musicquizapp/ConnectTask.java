package fi.tuni.musicquizapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage();
                Log.w("No response", mMessage);
                // call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String mMessage = response.body().string();
                try {
                    token = new JSONObject(mMessage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return null;
    }

    public JSONObject getToken() {
        return token;
    }
}
