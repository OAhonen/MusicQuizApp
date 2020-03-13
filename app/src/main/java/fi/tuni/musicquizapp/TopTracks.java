package fi.tuni.musicquizapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;

public class TopTracks extends AppCompatActivity {
    private HashMap<String, String> top10Songs;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toptracks);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (HashMap) extras.getSerializable("top10");
        }
        createArrayList();
        listView = findViewById(R.id.listID);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);
    }

    private void createArrayList() {
        arrayList = new ArrayList<>();
        for (HashMap.Entry<String, String> entry : top10Songs.entrySet()) {
            arrayList.add(entry.getKey() + " - " + entry.getValue());
        }
        Log.d("TOPTRACKS", arrayList.toString());
    }
}
