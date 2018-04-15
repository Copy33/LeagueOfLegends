package com.joemerhej.leagueoflegends.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.enums.Region;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.QueueRank;
import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;
import com.joemerhej.leagueoflegends.serverrequests.SummonerRequest;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesKey;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;
import com.joemerhej.leagueoflegends.utils.Utils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetConfigureActivity WidgetConfigureActivity}
 */
public class Widget extends AppWidgetProvider
{
    static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        Log.d("debug", "METHOD - ID " + appWidgetId + ": UpdateWidget");

        // get the count data to update from shared preferences
        int widgetCount = SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.COUNT, appWidgetId);
        ++widgetCount;

        // save data back the count to shared preferences
        SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.COUNT, appWidgetId, widgetCount);

        // get all other data from shared preferences
        String summonerName = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, appWidgetId);
        String soloDuoRank = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, appWidgetId); // TODO: take the highest rank instead of just solo queue
        Long leaguePoints = SharedPreferencesManager.readWidgetLong(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, appWidgetId);
        int rankImageId = SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, appWidgetId);

        // get the current time.
        String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());

        // get the widget's views (from widget or widget activity) and update them
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.widgetactiviy_summoner_name_text, summonerName);
        views.setTextViewText(R.id.widget_updated_text, context.getResources().getString(R.string.date_format, widgetCount, dateString));

        String summonerRank = soloDuoRank + " - " + leaguePoints + " LP";
        Bitmap myBitmap = Utils.getFontBitmap(context, summonerRank, Color.WHITE, 14);
        views.setImageViewBitmap(R.id.widget_rank_name_image, myBitmap);

        myBitmap = Utils.getFontBitmap(context, summonerName, Color.WHITE, 24);
        views.setImageViewBitmap(R.id.widget_summoner_name_image, myBitmap);

        views.setImageViewResource(R.id.widget_rank_image, rankImageId);

        // ===========================================================================================================================================================================
        // Buttons click listeners
        // ===========================================================================================================================================================================
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
        // ===========================================================================================================================================================================

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
    public void onReceive(final Context context, Intent intent)
    {
        // initialize shared preferences manager
        SharedPreferencesManager.init(context); //TODO: not sure if I need to call init shared prefs on receive

        // get the widget id
        final int widgetId;
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            widgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        else
        {
            widgetId = 0;
        }

        final Profile profile = new Profile();

        Log.d("debug", "METHOD - ID " + widgetId + ": OnReceive");

        // check which action is returned (ie. which button was pressed)
        if(intent.getAction().equals(WidgetAction.REFRESH.getValue()))
        {
            Log.d("debug", "ACTION - ID " + widgetId + ": Refresh ");

            // get the summoner name from shared preferences
            final String summonerName = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, widgetId);

            final SummonerRequest summonerRequest = new SummonerRequest(Region.EUNE);
            summonerRequest.getSummoner(summonerName, Utils.getApiKey(), new SummonerRequest.SummonerResponseCallback<Summoner>()
            {
                @Override
                public void onResponse(Summoner response, String error)
                {
                    if(response != null && error == null)
                    {
                        profile.set(response.getName(), response.getId(), response.getProfileIconId(), response.getSummonerLevel());

                        summonerRequest.getLeagueRanks(profile.getId().toString(), Utils.getApiKey(), new SummonerRequest.SummonerResponseCallback<List<RankedData>>()
                        {
                            @Override
                            public void onResponse(List<RankedData> response, String error)
                            {
                                if(response != null && error == null)
                                {
                                    for(RankedData rankedData : response)
                                    {
                                        switch(QueueType.from(rankedData.getQueueType()))
                                        {
                                            case FLEX_5V5:
                                                profile.setFlex5(new QueueRank(QueueType.FLEX_5V5, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                                break;
                                            case SOLO_DUO:
                                                profile.setSoloDuo(new QueueRank(QueueType.SOLO_DUO, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                                break;
                                            case FLEX_3V3:
                                                profile.setFlex3(new QueueRank(QueueType.FLEX_3V3, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                                break;
                                            default:
                                                profile.setRanks(new QueueRank(QueueType.SOLO_DUO), new QueueRank(QueueType.FLEX_5V5), new QueueRank(QueueType.FLEX_3V3));
                                                break;
                                        }
                                    }

                                    // get the rank image id
                                    final int rankImageId = context.getResources().getIdentifier(profile.getSoloDuo().getRank().getName().replace(" ", "_").toLowerCase(), "drawable", context.getPackageName());

                                    // store the info locally
                                    SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, widgetId, rankImageId);

                                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_NAME, widgetId, summonerName);
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_ID, widgetId, profile.getId());
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_ICON_ID, widgetId, profile.getProfileIconId());
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_LEVEL, widgetId, profile.getSummonerLevel());

                                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, widgetId, profile.getSoloDuo().getRank().getName());
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, widgetId, profile.getSoloDuo().getLeaguePoints());
                                    SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_SOLO_DUO_HOTSTREAK, widgetId, profile.getSoloDuo().getHotStreak());

                                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_FLEX_5_RANK, widgetId, profile.getFlex5().getRank().getName());
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_FLEX_5_LP, widgetId, profile.getFlex5().getLeaguePoints());
                                    SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_FLEX_5_HOTSTREAK, widgetId, profile.getFlex5().getHotStreak());

                                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_FLEX_3_RANK, widgetId, profile.getFlex3().getRank().getName());
                                    SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_FLEX_3_LP, widgetId, profile.getFlex3().getLeaguePoints());
                                    SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_FLEX_3_HOTSTREAK, widgetId, profile.getFlex3().getHotStreak());

                                    // it is the responsibility of the configuration activity to update the app widget
                                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                                    Widget.updateWidget(context, appWidgetManager, widgetId);
                                }
                                else if(error != null)
                                {
                                    Log.e("debug", "ERROR - 1: " + error);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t)
                            {
                                Log.e("debug", "ERROR - 2: " + t.getLocalizedMessage());
                            }
                        });
                    }
                    else
                    {
                        Log.e("debug", "ERROR - 3: " + error);
                    }
                }

                @Override
                public void onFailure(Throwable t)
                {
                    Log.e("debug", "ERROR - 4: " + t.getLocalizedMessage());
                }
            });
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

            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.COUNT, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.RANK_IMAGE_RES_ID, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_NAME, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_ID, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_ICON_ID, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_LEVEL, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_SOLO_DUO_HOTSTREAK, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_5_RANK, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_5_LP, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_5_HOTSTREAK, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_3_RANK, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_3_LP, appWidgetId);
            SharedPreferencesManager.removeWidgetPreference(SharedPreferencesKey.SUMMONER_FLEX_3_HOTSTREAK, appWidgetId);
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

