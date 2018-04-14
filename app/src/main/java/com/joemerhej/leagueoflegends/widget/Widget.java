package com.joemerhej.leagueoflegends.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.sharedpreferences.SPKey;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetConfigureActivity WidgetConfigureActivity}
 */
public class Widget extends AppWidgetProvider
{

    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        Log.d("debug", "METHOD: UpdateWidget");

        // get the data to update from shared preferences
        CharSequence widgetText = SharedPreferencesManager.readWidgetString(SPKey.TEXT_KEY, appWidgetId);
        int widgetCount = SharedPreferencesManager.readWidgetInt(SPKey.COUNT_KEY, appWidgetId);
        ++widgetCount;

        // save data back to shared preferences after it's updated
        SharedPreferencesManager.writeWidgetInt(SPKey.COUNT_KEY, appWidgetId, widgetCount);

        // get the current time.
        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // get the widget's views (from widget or widget activity) and update them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_id, String.valueOf(appWidgetId));
        views.setTextViewText(R.id.appwidget_update, context.getResources().getString(R.string.date_count_format, widgetCount, dateString));

        // setup update button to send an update request as a pending intent.
        Intent intentUpdate = new Intent(context, Widget.class);

        // the intent action must be an app widget update.
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        // include the widget ID to be updated as an intent extra.
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        // wrap it all in a pending intent to send a broadcast.
        //  use the app widget ID as the request code (2nd argument) so that each intent is unique.
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);

        // assign the pending intent to the button onClick handler
        views.setOnClickPendingIntent(R.id.button_update, pendingUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Log.d("debug", "METHOD: OnUpdate");

        // There may be multiple widgets active, so update all of them
        for(int appWidgetId : appWidgetIds)
        {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        Log.d("debug", "METHOD: OnDeleted");

        // When the user deletes the widget, delete the preference associated with it
        for(int appWidgetId : appWidgetIds)
        {
            SharedPreferencesManager.removeWidgetPreference(SPKey.TEXT_KEY, appWidgetId);
        }
    }

    // fired when first widget is created
    @Override
    public void onEnabled(Context context)
    {
        Log.d("debug", "METHOD: OnEnabled");

        // initialize shared preferences manager
        SharedPreferencesManager.init(context);
    }

    @Override
    public void onDisabled(Context context)
    {
        Log.d("debug", "METHOD: OnDisabled");

        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Log.d("debug", "METHOD: OnReceive");

        // initialize shared preferences manager
        SharedPreferencesManager.init(context); //TODO: not sure if I need to call init shared prefs on receive

        super.onReceive(context, intent);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions)
    {
        Log.d("debug", "METHOD: OnAppWidgetOptionsChanged");
    }
}

