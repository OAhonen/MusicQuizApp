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
    private static final String keyAccessTokenFetched = "accessTokenFetched";

    // Default country & countryCode = Finland
    private static String defCountryCode = "https://api.spotify.com/v1/playlists/37i9dQZEVXbMxcczTSoGwZ";
    private static String defCountry = "Finland";

    public static void init(Context context) {
        prefs = context.getSharedPreferences(keyPrefs, Context.MODE_PRIVATE);
    }

    public static void setCountry(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry, country);
        editor.commit();
    }

    public static String getCountry() {
        return prefs.getString(keyCountry, defCountry);
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

    public static void setAccessTokenFetched(long accessTokenFetched) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(keyAccessTokenFetched, accessTokenFetched);
        editor.commit();
    }

    public static long getAccessTokenFetched() {
        return prefs.getLong(keyAccessTokenFetched, 0);
    }

    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyAccessToken, accessToken);
        editor.commit();
    }

    public static String getAccessToken() {
        return prefs.getString(keyAccessToken, null);
    }
}
