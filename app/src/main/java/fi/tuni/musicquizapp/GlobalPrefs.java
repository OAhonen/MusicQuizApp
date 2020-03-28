package fi.tuni.musicquizapp;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class GlobalPrefs {
    private static SharedPreferences prefs;
    private static final String keyPrefs = "MusicQuizPrefs";
    private static final String keyCountry = "country";
    private static final String keyCountryCode = "countryCode";
    private static final String keyAccessToken = "accessToken";
    private static final String keyCountryNumber = "countryNumber";

    // Default countryCode = Finnish playlist
    private static String defCountryCode = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMxcczTSoGwZ";

    public static void init(Context context) {
        prefs = context.getSharedPreferences("MusicQuizPrefs", Context.MODE_PRIVATE);
    }

    public static void setCountry(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry, country);
        editor.commit();
    }

    public static String getCountry() {
        return prefs.getString(keyCountry, null);
    }

    public static void setCountryCode(String countryCode) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountryCode, countryCode);
        editor.commit();
    }

    public static String getCountryCode() {
        return prefs.getString(keyCountryCode, defCountryCode);
    }

    public static void setCountryNumber(int countryNumber) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyCountryNumber, countryNumber);
        editor.commit();
    }

    public static int getCountryNumber() {
        return prefs.getInt(keyCountryNumber, 0);
    }
}
