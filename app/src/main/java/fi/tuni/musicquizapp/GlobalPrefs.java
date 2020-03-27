package fi.tuni.musicquizapp;

import android.content.Context;
import android.content.SharedPreferences;

public abstract class GlobalPrefs {
    private static SharedPreferences prefs;
    private static final String keyPrefs = "MusicQuizPrefs";
    private static final String keyCountry = "country";
    private static final String keyCountryCode = "countryCode";
    private static final String keyAccessToken = "accessToken";

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
}
