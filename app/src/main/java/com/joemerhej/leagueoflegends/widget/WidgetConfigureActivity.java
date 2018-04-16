package com.joemerhej.leagueoflegends.widget;

import android.app.Activity;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity
{
    // properties
    private int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // views
    private LinearLayout mBackgroundLinearLayout;
    private EditText mSummonerNameEditText;
    private TextView mUpdatedTextView;
    private ImageView mRankImageImageView;
    private TextView mRankNameTextView;
    private TextView mSummonerNameTextView;
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

        // initialize the views
        mSummonerNameEditText = findViewById(R.id.widgetactiviy_summoner_name_text);
        mAddWidgetButton = findViewById(R.id.widgetactivity_add_button);
        mUpdatedTextView = findViewById(R.id.widgetactivity_updated_text);
        mRankImageImageView = findViewById(R.id.widgetactivity_rank_image);
        mRankNameTextView = findViewById(R.id.widgetactivity_rank_name_text);
        mSummonerNameTextView = findViewById(R.id.widgetactivity_summoner_name_text);

        // fill in the views from shared preferences if they exist
        if(mSummonerNameEditText.getText().toString().equals(""))
            mAddWidgetButton.setEnabled(false);

        mSummonerNameEditText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId));
        mSummonerNameEditText.setSelection(mSummonerNameEditText.getText().length());

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

                    mAddWidgetButton.setEnabled(true);
                    return true;
                }

                return false;
            }
        });

        mUpdatedTextView.setText(getResources().getString(R.string.date_format, 1, DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date())));

//        int rankImageIdNEW = getResources().getIdentifier(profile.getSoloDuo().getRank().getName().replace(" ", "_").toLowerCase(), "drawable", context.getPackageName());
//        mRankImageImageView.setImageResource();

        // add the click listener to the add widget button that will update the widget and add it (return its id)
        mAddWidgetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String summonerName = mSummonerNameEditText.getText().toString();

                // write the summoner name to shared preferences
                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.SUMMONER_NAME, mWidgetId, summonerName);

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
}

























