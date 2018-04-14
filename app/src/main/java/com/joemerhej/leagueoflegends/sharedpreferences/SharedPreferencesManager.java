package com.joemerhej.leagueoflegends.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Joe Merhej on 4/14/18.
 */


public class SharedPreferencesManager
{
    // instance of shared preferences
    private static SharedPreferences mSharedPref;

    // private constants
    private static final String WIDGET_KEY_PREFIX = "appwidget_";

    // public constants
    public static final String TEXT_KEY = "text_";
    public static final String COUNT_KEY = "count_";


    private SharedPreferencesManager()
    {
    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getApplicationContext().getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    }

    public static String readWidgetString(String key, int widgetId)
    {
        return mSharedPref.getString(WIDGET_KEY_PREFIX + key + widgetId, null);
    }

    public static void writeWidgetString(String key, int widgetId, String value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(WIDGET_KEY_PREFIX + key + widgetId, value);
        prefsEditor.apply();
    }

    public static boolean readWidgetBoolean(String key, int widgetId)
    {
        return mSharedPref.getBoolean(WIDGET_KEY_PREFIX + key + widgetId, false);
    }

    public static void writeWidgetBoolean(String key, int widgetId, boolean value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(WIDGET_KEY_PREFIX + key + widgetId, value);
        prefsEditor.apply();
    }

    public static int readWidgetInt(String key, int widgetId)
    {
        return mSharedPref.getInt(WIDGET_KEY_PREFIX + key + widgetId, -1);
    }

    public static void writeWidgetInt(String key, int widgetId, int value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(WIDGET_KEY_PREFIX + key + widgetId, value).apply();
    }

    public static void removeWidgetPreference(String key, int widgetId)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(WIDGET_KEY_PREFIX + key + widgetId).apply();
    }
}













