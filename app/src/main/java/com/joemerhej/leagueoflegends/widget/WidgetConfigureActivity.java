package com.joemerhej.leagueoflegends.widget;

import android.app.Activity;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.customviews.RegionSpinnerAdapter;
import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.enums.RegionCode;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.QueueRank;
import com.joemerhej.leagueoflegends.models.Region;
import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;
import com.joemerhej.leagueoflegends.serverrequests.SummonerRequest;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesKey;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;
import com.joemerhej.leagueoflegends.utils.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity
{
    // properties
    private int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private final Profile mProfile = new Profile();
    private boolean mIsNewProfile;

    // views
    private LinearLayout mBackgroundLinearLayout;
    private EditText mSummonerNameEditText;
    private Spinner mRegionSpinner;
    private TextView mUpdatedTextView;
    private ImageView mRankImageImageView;
    private ImageView mRankNameImageView;
    private ImageView mSummonerNameImageView;
    private Button mAddWidgetButton;


    public WidgetConfigureActivity()
    {
        super();
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

        // reset the isNewProfile variable
        mIsNewProfile = false;

        // initialize the views
        mSummonerNameEditText = findViewById(R.id.widgetactiviy_summoner_name_text);
        mRegionSpinner = findViewById(R.id.widgetactivity_region_spinner);
        mAddWidgetButton = findViewById(R.id.widgetactivity_add_button);
        mUpdatedTextView = findViewById(R.id.widgetactivity_updated_text);
        mRankImageImageView = findViewById(R.id.widgetactivity_rank_image);
        mRankNameImageView = findViewById(R.id.widgetactivity_rank_name_text);
        mSummonerNameImageView = findViewById(R.id.widgetactivity_summoner_name_text);

        // set up region spinner
        List<Region> regions = new ArrayList<>();
        regions.add(new Region("North America", RegionCode.NA, R.drawable.flag_na));
        regions.add(new Region("Korea", RegionCode.KR, R.drawable.flag_kr));
        regions.add(new Region("Japan", RegionCode.JP, R.drawable.flag_jp));
        regions.add(new Region("EU West", RegionCode.EUW, R.drawable.flag_euw));
        regions.add(new Region("EU Nordic & East", RegionCode.EUNE, R.drawable.flag_eune));
        regions.add(new Region("Oceania", RegionCode.OCE, R.drawable.flag_oce));
        regions.add(new Region("Brazil", RegionCode.BR, R.drawable.flag_br));
        regions.add(new Region("LAS", RegionCode.LAS, R.drawable.flag_las));
        regions.add(new Region("LAN", RegionCode.LAN, R.drawable.flag_lan));
        regions.add(new Region("Russia", RegionCode.RU, R.drawable.flag_ru));
        regions.add(new Region("Turkey", RegionCode.TR, R.drawable.flag_tr));
        regions.add(new Region("PBE", RegionCode.PBE, R.drawable.flag_pbe));

        RegionSpinnerAdapter regionSpinnerAdapter = new RegionSpinnerAdapter(this, regions);
        mRegionSpinner.setAdapter(regionSpinnerAdapter);
        mRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("debug", "Selected region number " + position);
                mRegionSpinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("debug", "Nothing Selected");
            }
        });

        // check if summoner name already populated (edit) or not (new widget)
        mSummonerNameEditText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId));
        String summonerName = mSummonerNameEditText.getText().toString();
        mAddWidgetButton.setEnabled(false);
        if(!summonerName.equals(""))
        {
            // if it's an edit, fill preview data from shared preferences
            mUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date())));

            String soloDuoRankSP = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, mWidgetId); // TODO: take the highest rank instead of just solo queue
            final Long leaguePointsSP = SharedPreferencesManager.readWidgetLong(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, mWidgetId);    // TODO: maybe use these values to check new vs. old?
            final int rankImageIdSP = SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, mWidgetId);

            // update the activity's views
            mSummonerNameEditText.setText(mSummonerNameEditText.getText().toString());
            mSummonerNameEditText.setSelection(mSummonerNameEditText.getText().length());

            final String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
            mUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, dateString));

            mRankImageImageView.setImageResource(rankImageIdSP);

            Context context = getApplicationContext();
            String summonerRank = soloDuoRankSP + " - " + leaguePointsSP + " LP"; // TODO: take the highest rank instead of just solo queue
            Bitmap summonerRankBitmap = Utils.getFontBitmap(context, summonerRank, Color.WHITE, 14);
            mRankNameImageView.setImageBitmap(summonerRankBitmap);

            Bitmap summonerNameBitmap = Utils.getFontBitmap(context, summonerName, Color.WHITE, 24);
            mSummonerNameImageView.setImageBitmap(summonerNameBitmap);

            mAddWidgetButton.setEnabled(true);
        }

        // listener to when keyboard action button (search) is clicked
        mSummonerNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    Log.d("debug", "clicked search!");

                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null && getCurrentFocus() != null)
                    {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }

                    // check summoner name and determine add widget button state
                    String summonerName = mSummonerNameEditText.getText().toString();
                    if(summonerName.isEmpty() || !summonerName.matches("^[0-9\\p{L} _\\.]+$"))
                    {
                        Log.e("debug", "Illegal Summoner Name: " + summonerName);

                        mAddWidgetButton.setEnabled(false);
                        return false;
                    }

                    // fetch new data and populate the preview
                    populatePreviewWithNewData(summonerName);
                }

                return true;
            }
        });

        // add the click listener to the add widget button that will update the widget and add it (return its id)
        mAddWidgetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                // if it's a new/edited profile save new data to shared preferences
                if(mIsNewProfile)
                {
                    mIsNewProfile = false;

                    // store the newly fetched info in SharedPreferences
                    final int rankImageIdNEW = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                    SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, mWidgetId, rankImageIdNEW);

                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId, mProfile.getSummonerName());
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
                }
                
                // it is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                Widget.updateWidget(getApplicationContext(), appWidgetManager, mWidgetId);

                // make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }

    void populatePreviewWithNewData(String summonerName)
    {
        // make the request to fetch new data
        final SummonerRequest summonerRequest = new SummonerRequest(RegionCode.EUNE);
        summonerRequest.getSummoner(summonerName, Utils.getApiKey(), new SummonerRequest.SummonerResponseCallback<Summoner>()
        {
            @Override
            public void onResponse(Summoner response, String error)
            {
                if(response != null && error == null)
                {
                    // fill the new data in the mmProfile
                    mProfile.set(response.getName(), response.getId(), response.getProfileIconId(), response.getSummonerLevel());

                    summonerRequest.getLeagueRanks(mProfile.getId().toString(), Utils.getApiKey(), new SummonerRequest.SummonerResponseCallback<List<RankedData>>()
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

                                // get the new rank image resource id
                                final int rankImageId = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().replace(" ", "_").toLowerCase(), "drawable", getPackageName());

                                // update the activity's views
                                final String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
                                mUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, dateString));
                                mRankImageImageView.setImageResource(rankImageId);

                                Context context = getApplicationContext();
                                String summonerRank = mProfile.getSoloDuo().getRank().getName() + " - " + mProfile.getSoloDuo().getLeaguePoints() + " LP"; // TODO: take the highest rank instead of just solo queue
                                Bitmap summonerRankBitmap = Utils.getFontBitmap(context, summonerRank, Color.WHITE, 14);
                                mRankNameImageView.setImageBitmap(summonerRankBitmap);

                                Bitmap summonerNameBitmap = Utils.getFontBitmap(context, mProfile.getSummonerName(), Color.WHITE, 24);
                                mSummonerNameImageView.setImageBitmap(summonerNameBitmap);

                                mAddWidgetButton.setEnabled(true);
                                mIsNewProfile = true;
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
}

























