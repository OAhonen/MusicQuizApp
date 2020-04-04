package fi.tuni.musicquizapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Saves playlist-information and access token.
 */
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

    /**
     * Initialize global prefs' SharedPreferences.
     * @param context context
     */
    public static void init(Context context) {
        prefs = context.getSharedPreferences(keyPrefs, Context.MODE_PRIVATE);
    }

    /**
     * Set current country.
     * @param country country-name
     */
    public static void setCountry(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry, country);
        editor.commit();
    }

    /**
     * Get current country.
     * @return country-name
     */
    public static String getCountry() {
        return prefs.getString(keyCountry, defCountry);
    }

    /**
     * Set current playlist's http-address.
     * @param countryCode http-address
     */
    public static void setCountryCode(String countryCode) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountryCode, countryCode);
        editor.commit();
    }

    /**
     * Get current playlist's http-address.
     * @return http-address
     */
    public static String getCountryCode() {
        return prefs.getString(keyCountryCode, defCountryCode);
    }

    /**
     * Set current playlist's number, which is used to get correct position in list in settings.
     * @param countryNumber countrynumber
     */
    public static void setCountryNumber(int countryNumber) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyCountryNumber, countryNumber);
        editor.commit();
    }

    /**
     * Get current playlist's number.
     * @return countrynumber
     */
    public static int getCountryNumber() {
        return prefs.getInt(keyCountryNumber, 0);
    }

    /**
     * Set time, when access token was last fetched.
     * @param accessTokenFetched time in milliseconds
     */
    public static void setAccessTokenFetched(long accessTokenFetched) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(keyAccessTokenFetched, accessTokenFetched);
        editor.commit();
    }

    /**
     * Get time, when access token was last fetched.
     * @return time in milliseconds
     */
    public static long getAccessTokenFetched() {
        return prefs.getLong(keyAccessTokenFetched, 0);
    }

    /**
     * Set access token.
     * @param accessToken access token
     */
    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyAccessToken, accessToken);
        editor.commit();
    }

    /**
     * Get access token.
     * @return access token
     */
    public static String getAccessToken() {
        return prefs.getString(keyAccessToken, null);
    }
}
