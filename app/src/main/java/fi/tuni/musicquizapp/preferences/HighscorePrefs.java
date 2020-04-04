package fi.tuni.musicquizapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Save high scores information.
 */
public class HighscorePrefs {
    private static SharedPreferences prefs;
    private static final String keyPrefs = "MusicQuizScorePrefs";
    private static final String keyName1 = "name1";
    private static final String keyCountry1 = "country1";
    private static final String keyScore1 = "score1";
    private static final String keyName2 = "name2";
    private static final String keyCountry2 = "country2";
    private static final String keyScore2 = "score2";
    private static final String keyName3 = "name3";
    private static final String keyCountry3 = "country3";
    private static final String keyScore3 = "score3";

    /**
     * Initialize high score's Shared Preferences.
     * @param context context
     */
    public static void init(Context context) {
        prefs = context.getSharedPreferences(keyPrefs, Context.MODE_PRIVATE);
    }

    /**
     * Set first place player name.
     * @param name name
     */
    public static void setName1(String name) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyName1, name);
        editor.commit();
    }

    /**
     * Get first place player name.
     * @return name
     */
    public static String getName1() {
        return prefs.getString(keyName1, null);
    }

    /**
     * Set second place player name.
     * @param name name
     */
    public static void setName2(String name) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyName2, name);
        editor.commit();
    }

    /**
     * Get second place player name.
     * @return name
     */
    public static String getName2() {
        return prefs.getString(keyName2, null);
    }

    /**
     * Set third place player name.
     * @param name name
     */
    public static void setName3(String name) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyName3, name);
        editor.commit();
    }

    /**
     * Get third place player name.
     * @return name
     */
    public static String getName3() {
        return prefs.getString(keyName3, null);
    }

    /**
     * Set first place playlist.
     * @param country playlist
     */
    public static void setCountry1(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry1, country);
        editor.commit();
    }

    /**
     * Get first place playlist.
     * @return playlist
     */
    public static String getCountry1() {
        return prefs.getString(keyCountry1, null);
    }

    /**
     * Set second place playlist.
     * @param country playlist
     */
    public static void setCountry2(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry2, country);
        editor.commit();
    }

    /**
     * Get second place playlist.
     * @return playlist
     */
    public static String getCountry2() {
        return prefs.getString(keyCountry2, null);
    }

    /**
     * Set third place playlist.
     * @param country playlist
     */
    public static void setCountry3(String country) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(keyCountry3, country);
        editor.commit();
    }

    /**
     * Get third place playlist.
     * @return playlist
     */
    public static String getCountry3() {
        return prefs.getString(keyCountry3, null);
    }

    /**
     * Set first place score.
     * @param score score
     */
    public static void setScore1(int score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyScore1, score);
        editor.commit();
    }

    /**
     * Get first place score.
     * @return score
     */
    public static int getScore1() {
        return prefs.getInt(keyScore1, 0);
    }

    /**
     * Set second place score.
     * @param score score
     */
    public static void setScore2(int score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyScore2, score);
        editor.commit();
    }

    /**
     * Get second place score.
     * @return score
     */
    public static int getScore2() {
        return prefs.getInt(keyScore2, 0);
    }

    /**
     * Set third place score.
     * @param score score
     */
    public static void setScore3(int score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(keyScore3, score);
        editor.commit();
    }

    /**
     * Get third place score.
     * @return score
     */
    public static int getScore3() {
        return prefs.getInt(keyScore3, 0);
    }
}
