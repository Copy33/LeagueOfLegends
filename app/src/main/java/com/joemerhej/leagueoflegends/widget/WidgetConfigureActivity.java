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
import com.joemerhej.leagueoflegends.adapters.RegionSpinnerAdapter;
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
import com.joemerhej.leagueoflegends.utils.Regions;
import com.joemerhej.leagueoflegends.utils.Utils;

import java.text.DateFormat;
import java.util.ArrayList;
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
    private RegionSpinnerAdapter mRegionSpinnerAdapter;
    private TextView mPreviewUpdatedTextView;
    private ImageView mPreviewRankImageImageView;
    private ImageView mPreviewRankNameImageView;
    private ImageView mPreviewSummonerNameImageView;
    private Button mAddWidgetButton;


    public WidgetConfigureActivity()
    {
        super();
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        Log.d("asd", "METHOD - Activity: OnCreate");

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
        mPreviewUpdatedTextView = findViewById(R.id.widgetactivity_updated_text);
        mPreviewRankImageImageView = findViewById(R.id.widgetactivity_rank_image);
        mPreviewRankNameImageView = findViewById(R.id.widgetactivity_rank_name_text);
        mPreviewSummonerNameImageView = findViewById(R.id.widgetactivity_summoner_name_text);

        // set up region spinner
        List<Region> regions = new ArrayList<>();
        regions.add(Regions.NORTH_AMERICA);
        regions.add(Regions.KOREA);
        regions.add(Regions.JAPAN);
        regions.add(Regions.EUROPE_WEST);
        regions.add(Regions.EUROPE_NORDIC_AND_EAST);
        regions.add(Regions.OCEANIA);
        regions.add(Regions.BRAZIL);
        regions.add(Regions.LATIN_AMERICA_SOUTH);
        regions.add(Regions.LATIN_AMERICA_NORTH);
        regions.add(Regions.RUSSIA);
        regions.add(Regions.TURKEY);
        regions.add(Regions.PUBLIC_BETA);

        mRegionSpinnerAdapter = new RegionSpinnerAdapter(this, regions);
        mRegionSpinner.setAdapter(mRegionSpinnerAdapter);

        // region change listener
        mRegionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d("asd", "Selected region number " + position);
                mRegionSpinner.setSelection(position);

                // clear preview if selected position is different from shared preferences stored region

                String regionCodeSP = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.REGION_CODE, mWidgetId);
                Region regionSP = Utils.getRegionFromCode(RegionCode.from(regionCodeSP));
                int positionSP = mRegionSpinnerAdapter.getRegions().indexOf(regionSP);

                if(position != positionSP)
                    clearSummonerConfiguration();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
                Log.d("asd", "Nothing Selected");
            }
        });

        // check if summoner name already populated (edit) or not (new widget)
        mSummonerNameEditText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId));
        String summonerName = mSummonerNameEditText.getText().toString();
        mAddWidgetButton.setEnabled(false);

        // if it's an edit, just fill the preview and the profile region
        if(!summonerName.equals(""))
        {
            // get profile region
            mProfile.setRegion(Utils.getRegionFromCode(RegionCode.from(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.REGION_CODE, mWidgetId))));

            // set the spinner to the right region
            mRegionSpinner.setSelection(mRegionSpinnerAdapter.getRegions().indexOf(mProfile.getRegion()));

            // fill preview data from shared preferences
            mPreviewUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date())));

            String soloDuoRankSP = SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_SOLO_DUO_RANK, mWidgetId); // TODO: take the highest rank instead of just solo queue
            final Long leaguePointsSP = SharedPreferencesManager.readWidgetLong(SharedPreferencesKey.SUMMONER_SOLO_DUO_LP, mWidgetId);
            final int rankImageIdSP = SharedPreferencesManager.readWidgetInt(SharedPreferencesKey.RANK_IMAGE_RES_ID, mWidgetId);

            // update the preview's views
            mSummonerNameEditText.setText(mSummonerNameEditText.getText().toString());
            mSummonerNameEditText.setSelection(mSummonerNameEditText.getText().length());

            final String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
            mPreviewUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, dateString));

            mPreviewRankImageImageView.setImageResource(rankImageIdSP);

            Context context = getApplicationContext();
            String summonerRank = soloDuoRankSP + " - " + leaguePointsSP + " LP"; // TODO: take the highest rank instead of just solo queue
            Bitmap summonerRankBitmap = Utils.getFontBitmap(context, summonerRank, Color.WHITE, 14);
            mPreviewRankNameImageView.setImageBitmap(summonerRankBitmap);

            Bitmap summonerNameBitmap = Utils.getFontBitmap(context, summonerName, Color.WHITE, 24);
            mPreviewSummonerNameImageView.setImageBitmap(summonerNameBitmap);

            mAddWidgetButton.setEnabled(true);
        }

        // keyboard search button listener
        mSummonerNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if(actionId == EditorInfo.IME_ACTION_SEARCH)
                {
                    Log.d("asd", "clicked search!");

                    // hide virtual keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm != null && getCurrentFocus() != null)
                    {
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }

                    // if summoner name is invalid, disable add widget button
                    String summonerName = mSummonerNameEditText.getText().toString();
                    if(summonerName.isEmpty() || !summonerName.matches("^[0-9\\p{L} _\\.]+$"))
                    {
                        Log.e("asd", "Illegal Summoner Name: " + summonerName);

                        mAddWidgetButton.setEnabled(false);
                        return false;
                    }

                    // if summoner name is valid, fetch new data and populate the preview
                    populatePreviewWithNewData(summonerName);
                }

                return true;
            }
        });

        // add widget button listener
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

                    SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.REGION_CODE, mWidgetId, mProfile.getRegion().getCode().value());
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

    // makes a summoner request and populates the preview with new data
    void populatePreviewWithNewData(String summonerName)
    {
        // make the request to fetch new data
        final Region selectedRegion = mRegionSpinnerAdapter.getRegions().get(mRegionSpinner.getSelectedItemPosition());
        final SummonerRequest summonerRequest = new SummonerRequest(selectedRegion.getCode());
        summonerRequest.getSummoner(summonerName, Utils.getApiKey(), new SummonerRequest.SummonerResponseCallback<Summoner>()
        {
            @Override
            public void onResponse(Summoner response, String error)
            {
                if(response != null && error == null)
                {
                    // fill the new data in mProfile
                    mProfile.set(selectedRegion, response.getName(), response.getId(), response.getProfileIconId(), response.getSummonerLevel());

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

                                // update the preview's views
                                final String dateString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
                                mPreviewUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, dateString));
                                mPreviewRankImageImageView.setImageResource(rankImageId);
                                mPreviewRankImageImageView.setVisibility(View.VISIBLE);

                                Context context = getApplicationContext();
                                String summonerRank = mProfile.getSoloDuo().getRank().getName() + " - " + mProfile.getSoloDuo().getLeaguePoints() + " LP"; // TODO: take the highest rank instead of just solo queue
                                Bitmap summonerRankBitmap = Utils.getFontBitmap(context, summonerRank, Color.WHITE, 14);
                                mPreviewRankNameImageView.setImageBitmap(summonerRankBitmap);

                                Bitmap summonerNameBitmap = Utils.getFontBitmap(context, mProfile.getSummonerName(), Color.WHITE, 24);
                                mPreviewSummonerNameImageView.setImageBitmap(summonerNameBitmap);

                                mAddWidgetButton.setEnabled(true);
                                mIsNewProfile = true;
                            }
                            else if(error != null)
                            {
                                Log.e("asd", "ERROR - 1: " + error);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t)
                        {
                            Log.e("asd", "ERROR - 2: " + t.getLocalizedMessage());
                        }
                    });
                }
                else
                {
                    Log.e("asd", "ERROR - 3: " + error);
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                Log.e("asd", "ERROR - 4: " + t.getLocalizedMessage());
            }
        });
    }

    private void clearSummonerConfiguration()
    {
        Log.d("asd", "CLEAR SUMMONER CONFIGURATION");
        mSummonerNameEditText.setText("");
        mAddWidgetButton.setEnabled(false);

        mPreviewRankImageImageView.setVisibility(View.INVISIBLE);
        Bitmap rankNameBitmap = Utils.getFontBitmap(WidgetConfigureActivity.this, " ", Color.WHITE, 24);
        mPreviewRankNameImageView.setImageBitmap(rankNameBitmap);
        Bitmap summonerNameBitmap = Utils.getFontBitmap(WidgetConfigureActivity.this, " ", Color.WHITE, 24);
        mPreviewSummonerNameImageView.setImageBitmap(summonerNameBitmap);
    }
}

























