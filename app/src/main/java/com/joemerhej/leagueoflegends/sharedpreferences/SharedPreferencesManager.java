package com.joemerhej.leagueoflegends.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.QueueRank;
import com.joemerhej.leagueoflegends.models.WidgetListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static String readWidgetString(SharedPreferencesKey key, int widgetId)
    {
        return mSharedPref.getString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, "");
    }

    public static void writeWidgetString(SharedPreferencesKey key, int widgetId, String value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value);
        prefsEditor.apply();
    }

    public static boolean readWidgetBoolean(SharedPreferencesKey key, int widgetId)
    {
        return mSharedPref.getBoolean(WIDGET_KEY_PREFIX + key.getValue() + widgetId, false);
    }

    public static void writeWidgetBoolean(SharedPreferencesKey key, int widgetId, boolean value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value);
        prefsEditor.apply();
    }

    public static int readWidgetInt(SharedPreferencesKey key, int widgetId)
    {
        return mSharedPref.getInt(WIDGET_KEY_PREFIX + key.getValue() + widgetId, -1);
    }

    public static void writeWidgetInt(SharedPreferencesKey key, int widgetId, int value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value).apply();
    }

    public static Long readWidgetLong(SharedPreferencesKey key, int widgetId)
    {
        String longString = mSharedPref.getString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, "");

        Long result = 0L;
        try
        {
            result = Long.valueOf(longString);
        }
        catch(NumberFormatException e)
        {
            return 0L;
        }

        return result;
    }

    public static void writeWidgetLong(SharedPreferencesKey key, int widgetId, Long value)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(WIDGET_KEY_PREFIX + key.getValue() + widgetId, value.toString());
        prefsEditor.apply();
    }

    public static void removeWidgetPreference(SharedPreferencesKey key, int widgetId)
    {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.remove(WIDGET_KEY_PREFIX + key.getValue() + widgetId).apply();
    }

    public static void readWidgetListItems(List<WidgetListItem> items)
    {
        if(items == null)
            return;

        items.clear();

        List<Integer> allWidgetIds = new ArrayList<>();

        // loop through all shared preferences
        Map<String, ?> allEntries = mSharedPref.getAll();
        for(Map.Entry<String, ?> entry : allEntries.entrySet())
        {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());

            String key = entry.getKey();

            String[] splits = key.split("_");
            String widgetIdNum = splits[splits.length - 1];
            int widgetId = Integer.parseInt(widgetIdNum);

            if(!allWidgetIds.contains(widgetId))
            {
                allWidgetIds.add(widgetId);

                WidgetListItem widgetListItem = new WidgetListItem();
                widgetListItem.setWidgetId(widgetId);
                widgetListItem.setSummonerName(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, widgetId));
                widgetListItem.setRankImageId(SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, widgetId));

                items.add(widgetListItem);
            }
        }
    }
}













