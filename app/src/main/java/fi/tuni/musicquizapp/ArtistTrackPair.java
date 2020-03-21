package fi.tuni.musicquizapp;

import java.io.Serializable;

public class ArtistTrackPair implements Serializable {
    private String artist;
    private String track;

    public ArtistTrackPair(String artist, String track) {
        this.artist = artist;
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
