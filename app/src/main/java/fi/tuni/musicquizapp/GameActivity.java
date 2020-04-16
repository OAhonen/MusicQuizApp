package fi.tuni.musicquizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import fi.tuni.musicquizapp.preferences.GlobalPrefs;

/**
 * Game activity.
 */
public class GameActivity extends AppCompatActivity {
    private ArrayList<ArtistTrackPair> top10Songs;
    private TextView textView;
    private String[] artists = new String[10];
    private String[] tracks = new String[10];
    private Button b1;
    private Button b2;
    private Button b3;
    private TextView textPreview;
    private int round = 0;
    private boolean[] userAnswers = new boolean[10];
    private String mode;
    private String hideArtist = "Hide artist";
    private ArrayList<String> top10PreviewUrls;
    private SimpleExoPlayer exoPlayer;
    private PlayerView exoPlayerView;
    private MediaSource mediaSource;
    private DefaultHttpDataSourceFactory dataSourceFactory;
    private ExtractorsFactory extractorsFactory;

    /**
     * Get top-10 tracks from extras.
     * @param savedInstanceState bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().hide();
        mode = GlobalPrefs.getMode();
        textView = findViewById(R.id.questionID);
        b1 = findViewById(R.id.answer1);
        b2 = findViewById(R.id.answer2);
        b3 = findViewById(R.id.answer3);
        textPreview = findViewById(R.id.listenPreviewText);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            top10Songs = (ArrayList) extras.getSerializable("top10");
            top10PreviewUrls = (ArrayList) extras.getSerializable("top10urls");
        }
        setCorrectOrder();
        setUpQuestion(round);
        setButtons(round);
        setUpExoPlayer();
    }

    /**
     * Setup ExoPlayer.
     */
    private void setUpExoPlayer() {
        exoPlayerView = findViewById(R.id.playerView);
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_music");
            extractorsFactory = new DefaultExtractorsFactory();
            exoPlayerView.setPlayer(exoPlayer);
            if (!top10PreviewUrls.get(round).equals("null")) {
                Uri uri = Uri.parse(top10PreviewUrls.get(round));
                mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
                exoPlayer.prepare(mediaSource);
                textPreview.setText("Listen preview (buffering can sometimes take a while).");
            }
        }catch (Exception e){
            Log.e("GAME","Exoplayer error: "+ e.toString());
        }
    }

    /**
     * Create question to the user.
     * @param turn current turn
     */
    private void setUpQuestion(int turn) {
        if (mode.equals(hideArtist)) {
            textView.setText("Track: " + tracks[turn] + "\nWhose track is this?");
        } else {
            textView.setText("Artist: " + tracks[turn] + "\nWhich track is made by this artist?");
        }
    }

    /**
     * Check if preview is available for current track.
     */
    private void checkPreview() {
        if (top10PreviewUrls.get(round).equals("null")) {
            textPreview.setText("No preview available for this track.");
        } else {
            textPreview.setText("Listen preview (buffering can sometimes take a while).");
            Uri uri = Uri.parse(top10PreviewUrls.get(round));
            mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            exoPlayer.prepare(mediaSource);
        }
    }

    /**
     * Put artists and tracks to their own array.
     */
    private void setCorrectOrder() {
        for (int i = 0; i < top10Songs.size(); i++) {
            if (mode.equals(hideArtist)) {
                artists[i] = top10Songs.get(i).getArtist();
                tracks[i] = top10Songs.get(i).getTrack();
            } else {
                artists[i] = top10Songs.get(i).getTrack();
                tracks[i] = top10Songs.get(i).getArtist();
            }
        }
    }

    /**
     * Put randomly the correct answer to one of the buttons.
     * Check that all the button's have different answer.
     * @param turn current turn
     */
    private void setButtons(int turn) {
        Random random = new Random();
        int r = random.nextInt(4-1) + 1;
        int r2 = random.nextInt(9);
        int r3 = random.nextInt(9);
        String correctAnswer = artists[turn];
        String answer2 = artists[r2];
        String answer3 = artists[r3];

        if (r == 1) {
            b1.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b2.setText(answer2);
            b3.setText(answer3);
        } else if (r == 2) {
            b2.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b3.setText(answer3);
        } else if (r == 3) {
            b3.setText(correctAnswer);
            while (correctAnswer.equals(answer2) || correctAnswer.equals(answer3) || answer2.equals(answer3)) {
                r2 = random.nextInt(9);
                r3 = random.nextInt(9);
                if (!mode.equals(hideArtist)) {
                    if (!checkDoubles(r2, r3)) {
                        continue;
                    }
                }
                answer2 = artists[r2];
                answer3 = artists[r3];
            }
            b1.setText(answer2);
            b2.setText(answer3);
        }
    }

    /**
     * Check that there are no double anwers. For example, one artist could have
     * many tracks in top-10, so check that answers aren't from the same artist.
     * @param n index n
     * @param m index m
     * @return false/true
     */
    private boolean checkDoubles(int n, int m) {
        if (top10Songs.get(n).getArtist().equals(top10Songs.get(m).getArtist())) {
            return false;
        }
        return true;
    }

    /**
     * Check user's answer and put it to array. Also check if game is over.
     * @param v view
     */
    public void answered(View v) {
        if (v.getId() == R.id.answer1) {
            if (b1.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        } else if (v.getId() == R.id.answer2) {
            if (b2.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        } else if (v.getId() == R.id.answer3) {
            if (b3.getText().equals(artists[round])) {
                Log.d("GAME", "CORRECT");
                userAnswers[round] = true;
            } else {
                Log.d("GAME", "WRONG");
                userAnswers[round] = false;
            }
        }

        if (round < 9) {
            exoPlayer.stop();
            round++;
            setUpQuestion(round);
            setButtons(round);
            checkPreview();
        } else {
            exoPlayer.stop();
            exoPlayer.release();
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("userAnswers", userAnswers);
            intent.putExtra("top10", top10Songs);
            intent.putExtra("top10urls", top10PreviewUrls);
            startActivity(intent);
        }
    }
}
