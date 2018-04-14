package com.joemerhej.leagueoflegends.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.joemerhej.leagueoflegends.R;
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
        // get the data to update from shared preferences
        CharSequence widgetText = SharedPreferencesManager.readWidgetString(SharedPreferencesManager.TEXT_KEY, appWidgetId);

        // Get the current time.
        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // get the widget's views (from widget or widget activity) and update them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setTextViewText(R.id.appwidget_id, String.valueOf(appWidgetId));
        //views.setTextViewText(R.id.appwidget_update, context.getResources().getString(R.string.date_count_format, count, dateString));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them
        for(int appWidgetId : appWidgetIds)
        {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds)
    {
        // When the user deletes the widget, delete the preference associated with it
        for(int appWidgetId : appWidgetIds)
        {
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesManager.TEXT_KEY, appWidgetId);
        }
    }

    // fired when first widget is created
    @Override
    public void onEnabled(Context context)
    {
        // initialize shared preferences manager
        SharedPreferencesManager.init(context);
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}

