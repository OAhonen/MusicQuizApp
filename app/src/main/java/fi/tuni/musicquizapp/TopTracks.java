package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.GlobalPrefs;

/**
 * Current top-10 tracks.
 */
public class TopTracks extends AppCompatActivity {
    private ArrayList<ArtistTrackPair> top10Songs;
    private ArrayList<String> top10PreviewUrls;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;
    private TextView curCountry;

    /**
     * Get top-10 tracks from extras and put them to listview.
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toptracks);
        getSupportActionBar().hide();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList<ArtistTrackPair>) extras.getSerializable("top10");
            top10PreviewUrls = (ArrayList) extras.getSerializable("top10urls");
        }
        setArrayListForAdapter();
        listView = findViewById(R.id.listID);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_top10, arrayList);
        listView.setAdapter(arrayAdapter);
        curCountry = findViewById(R.id.showCountryID);
        curCountry.setText(GlobalPrefs.getCountry());
    }

    /**
     * Set up arraylist.
     */
    private void setArrayListForAdapter() {
        arrayList = new ArrayList<>();
        for (int i = 0; i < top10Songs.size(); i++) {
            arrayList.add(i+1 + ". " + top10Songs.get(i).getArtist() + " - " + top10Songs.get(i).getTrack());
        }
    }
}
