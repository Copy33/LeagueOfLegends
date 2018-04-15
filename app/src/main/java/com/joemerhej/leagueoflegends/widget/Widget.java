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
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesKey;
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
        Log.d("debug", "METHOD - ID " + appWidgetId + ": UpdateWidget");

        // get the data to update from shared preferences
        CharSequence widgetText = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.TEXT_KEY, appWidgetId);
        int widgetCount = SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.COUNT_KEY, appWidgetId);
        ++widgetCount;

        // save data back to shared preferences after it's updated
        SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.COUNT_KEY, appWidgetId, widgetCount);

        // get the current time.
        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // get the widget's views (from widget or widget activity) and update them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.widget_updated_text, context.getResources().getString(R.string.date_format, widgetCount, dateString));


        // setup refresh button to send a refresh action to the Widget class as a pending intent, include widget ID as extra,
        // wrap it in a pending intent to send a broadcast (pass widget id as request code to make each intent unique, and assign pending intent to click handler of button.
        Intent intentRefresh = new Intent(context, Widget.class);
        intentRefresh.setAction(WidgetAction.REFRESH.getValue());
        intentRefresh.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context, appWidgetId, intentRefresh, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.button_refresh, pendingIntentRefresh);


        // setup edit button to send an edit action to the Widget Configure Activity as a pending intent, include widget ID as extra,
        // wrap it in a pending intent to send a broadcast (pass widget id as request code to make each intent unique, and assign pending intent to click handler of button.
        Intent intentEdit = new Intent(context, WidgetConfigureActivity.class);
        intentEdit.setAction(WidgetAction.EDIT.getValue());
        intentEdit.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

        PendingIntent pendingIntentEdit = PendingIntent.getActivity(context, appWidgetId, intentEdit, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.button_edit, pendingIntentEdit);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        for(int appWidgetId : appWidgetIds)
        {
            Log.d("debug", "METHOD - ID " + appWidgetId + ": OnUpdate");
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        // initialize shared preferences manager
        SharedPreferencesManager.init(context); //TODO: not sure if I need to call init shared prefs on receive

        // get the widget id
        int widgetId = 0;
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        Log.d("debug", "METHOD - ID " + widgetId + ": OnReceive");

        // check which action is returned (ie. which button was pressed)
        if(intent.getAction().equals(WidgetAction.REFRESH.getValue()))
        {
            Log.d("debug", "ACTION - ID " + widgetId + ": Refresh ");

            // do the refresh action changes....

            Widget.updateWidget(context, AppWidgetManager.getInstance(context), widgetId);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        // When the user deletes the widget, delete the preference associated with it
        for(int appWidgetId : appWidgetIds)
        {
            Log.d("debug", "METHOD - ID " + appWidgetId + ": OnDeleted");

            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.TEXT_KEY, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.COUNT_KEY, appWidgetId);
        }
    }

    // fired when first widget is created
    @Override
    public void onEnabled(Context context)
    {
        Log.d("debug", "METHOD: OnEnabled - FIRST WIDGET");

        // initialize shared preferences manager
        SharedPreferencesManager.init(context);
    }

    @Override
    public void onDisabled(Context context)
    {
        Log.d("debug", "METHOD: OnDisabled - LAST WIDGET");

        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions)
    {
        Log.d("debug", "METHOD - ID " + appWidgetId + ": OnAppWidgetOptionsChanged");
    }
}

