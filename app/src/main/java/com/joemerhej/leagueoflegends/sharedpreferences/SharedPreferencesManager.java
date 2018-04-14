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


    private SharedPreferencesManager()
    {
    }

    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getApplicationContext().getSharedPreferences(context.getApplicationContext().getPackageName(), Context.MODE_PRIVATE);
    }

    public static String readWidgetString(SPKey key, int widgetId)
    {
        return mSharedPref.getString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, null);
    }

    public static void writeWidgetString(SPKey key, int widgetId, String value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value);
        prefsEditor.apply();
    }

    public static boolean readWidgetBoolean(SPKey key, int widgetId)
    {
        return mSharedPref.getBoolean(WIDGET_KEY_PREFIX + key.getValue() + widgetId, false);
    }

    public static void writeWidgetBoolean(SPKey key, int widgetId, boolean value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value);
        prefsEditor.apply();
    }

    public static int readWidgetInt(SPKey key, int widgetId)
    {
        return mSharedPref.getInt(WIDGET_KEY_PREFIX + key.getValue() + widgetId, -1);
    }

    public static void writeWidgetInt(SPKey key, int widgetId, int value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value).apply();
    }

    public static void removeWidgetPreference(SPKey key, int widgetId)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(WIDGET_KEY_PREFIX + key.getValue() + widgetId).apply();
    }
}













