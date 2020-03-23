package fi.tuni.musicquizapp;

import java.io.Serializable;

/**
 * Create "pairs" from artist and track.
 */
public class ArtistTrackPair implements Serializable {
    private String artist;
    private String track;

    /**
     * Constructor
     * @param artist artist name
     * @param track track name
     */
    public ArtistTrackPair(String artist, String track) {
        this.artist = artist;
        this.track = track;
    }

    /**
     * Getter for artist
     * @return artist-name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setter for artist
     * @param artist artist-name
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Getter for track
     * @return track-name
     */
    public String getTrack() {
        return track;
    }

    /**
     * Setter for track
     * @param track track-name
     */
    public void setTrack(String track) {
        this.track = track;
    }
}
