package com.joemerhej.leagueoflegends.widget;

import android.app.Activity;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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

import java.util.List;

/**
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity
{
    // constants
    private final String mApiKey = "RGAPI-45a15438-c837-4362-8c47-d523c6037b20";

    // properties
    private Profile mProfile;
    private int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // views
    private LinearLayout mBackgroundLinearLayout;
    private EditText mSummonerNameEditText;
    private Button mAddWidgetButton;


    public WidgetConfigureActivity()
    {
        super();
        mProfile = new Profile();
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        Log.d("debug", "METHOD - Activity: OnCreate");

        super.onCreate(icicle);

        // if user cancels, widget isn't created
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_configure_activity);

        // initialize activity background to be the device home wallpaper
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        Drawable wallpaperDrawable = wallpaperManager.getDrawable();
        mBackgroundLinearLayout = findViewById(R.id.activity_layout);
        mBackgroundLinearLayout.setBackground(wallpaperDrawable);

        // initialize shared preferences manager
        SharedPreferencesManager.init(this);

        // grab the widget id from the intent that launched the activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
        {
            mWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // if this activity was started with an intent without an app widget ID, finish it.
        if(mWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            finish();
            return;
        }

        // initialize the views
        mSummonerNameEditText = findViewById(R.id.widgetactiviy_summoner_name_text);
        mAddWidgetButton = findViewById(R.id.widgetactivity_add_button);

        // fill in the views
        mSummonerNameEditText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId));
        mSummonerNameEditText.setSelection(mSummonerNameEditText.getText().length());

        mAddWidgetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String summonerName = mSummonerNameEditText.getText().toString();
                if(summonerName.isEmpty())
                    summonerName = "Mojojo";

                getSummonerProfileAndRankedData(summonerName);
            }
        });
    }

    void getSummonerProfileAndRankedData(final String summonerName)
    {
        final SummonerRequest summonerRequest = new SummonerRequest(Region.EUNE);
        summonerRequest.getSummoner(summonerName, mApiKey, new SummonerRequest.SummonerResponseCallback<Summoner>()
        {
            @Override
            public void onResponse(Summoner response, String error)
            {
                if(response != null && error == null)
                {
                    mProfile.set(response.getName(), response.getId(), response.getProfileIconId(), response.getSummonerLevel());

                    summonerRequest.getLeagueRanks(mProfile.getId().toString(), mApiKey, new SummonerRequest.SummonerResponseCallback<List<RankedData>>()
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
                                            mProfile.setFlex5(new QueueRank(QueueType.FLEX_5V5, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                            break;
                                        case SOLO_DUO:
                                            mProfile.setSoloDuo(new QueueRank(QueueType.SOLO_DUO, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                            break;
                                        case FLEX_3V3:
                                            mProfile.setFlex3(new QueueRank(QueueType.FLEX_3V3, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                            break;
                                        default:
                                            mProfile.setRanks(new QueueRank(QueueType.SOLO_DUO), new QueueRank(QueueType.FLEX_5V5), new QueueRank(QueueType.FLEX_3V3));
                                            break;
                                    }
                                }

                                // fill in the views
//                                mProfileName.setText(mProfile.getName());
//                                final int id = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().toLowerCase(), "drawable", getPackageName());
//                                mRankImage.setImageResource(id);

//                                getPatchVersionsAndSummonerIcon();

                                // get the rank image id
                                final int rankImageId = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().replace(" ", "_").toLowerCase(), "drawable", getPackageName());

                                // store the info locally
                                SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.COUNT, mWidgetId, 0);
                                SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, mWidgetId, rankImageId);

                                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId, summonerName);
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_ID, mWidgetId, mProfile.getId());
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_ICON_ID, mWidgetId, mProfile.getProfileIconId());
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_LEVEL, mWidgetId, mProfile.getSummonerLevel());

                                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, mWidgetId, mProfile.getSoloDuo().getRank().getName());
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, mWidgetId, mProfile.getSoloDuo().getLeaguePoints());
                                SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_SOLO_DUO_HOTSTREAK, mWidgetId, mProfile.getSoloDuo().getHotStreak());

                                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_FLEX_5_RANK, mWidgetId, mProfile.getFlex5().getRank().getName());
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_FLEX_5_LP, mWidgetId, mProfile.getFlex5().getLeaguePoints());
                                SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_FLEX_5_HOTSTREAK, mWidgetId, mProfile.getFlex5().getHotStreak());

                                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_FLEX_3_RANK, mWidgetId, mProfile.getFlex3().getRank().getName());
                                SharedPreferencesManager.writeWidgetLong(SharedPreferencesKey.SUMMONER_FLEX_3_LP, mWidgetId, mProfile.getFlex3().getLeaguePoints());
                                SharedPreferencesManager.writeWidgetBoolean(SharedPreferencesKey.SUMMONER_FLEX_3_HOTSTREAK, mWidgetId, mProfile.getFlex3().getHotStreak());

                                // it is the responsibility of the configuration activity to update the app widget
                                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                                Widget.updateWidget(getApplicationContext(), appWidgetManager, mWidgetId);

                                // make sure we pass back the original appWidgetId
                                Intent resultValue = new Intent();
                                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                                setResult(RESULT_OK, resultValue);
                                finish();
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

//    void getPatchVersionsAndSummonerIcon()
//    {
//        final GeneralRequest generalRequest = new GeneralRequest(Region.EUNE);
//        generalRequest.getPatchVersions(mApiKey, new GeneralRequest.GeneralResponseCallback<List<String>>()
//        {
//            @Override
//            public void onResponse(List<String> response, String error)
//            {
//                if(response != null && error == null)
//                {
//                    String currentPatch = response.get(0);
//                    String iconUrl = "https://ddragon.leagueoflegends.com/cdn/" + currentPatch + "/img/profileicon/" + mProfile.getProfileIconId().toString() + ".png";
//
//                    Picasso.get()
//                           .load(iconUrl)
//                           .fit()
//                           .into(mProfileIcon);
//                }
//                else
//                {
//                    mProfileName.setText("ERROR: " + error);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t)
//            {
//                mProfileName.setText("ERROR: " + t.getLocalizedMessage());
//            }
//        });
//    }
}

























