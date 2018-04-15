package com.joemerhej.leagueoflegends.widget;

import android.app.Activity;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesKey;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;

/**
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity
{
    // properties
    int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // views
    LinearLayout mBackgroundLinearLayout;
    EditText mSummonerNameEditText;
    Button mAddWidgetButton;


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
        setContentView(R.layout.widget_configure);

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
        mSummonerNameEditText = findViewById(R.id.appwidget_text);
        mAddWidgetButton = findViewById(R.id.add_button);

        // fill in the views
        mSummonerNameEditText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.TEXT_KEY, mWidgetId));
        mSummonerNameEditText.setSelection(mSummonerNameEditText.getText().length());

        mAddWidgetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Context context = WidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                String widgetText = mSummonerNameEditText.getText().toString();
                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.TEXT_KEY, mWidgetId, widgetText);
                SharedPreferencesManager.writeWidgetInt(SharedPreferencesKey.COUNT_KEY, mWidgetId, 0);

                // It is the responsibility of the configuration activity to update the app widget
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                Widget.updateWidget(context, appWidgetManager, mWidgetId);

                // Make sure we pass back the original appWidgetId
                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetId);
                setResult(RESULT_OK, resultValue);
                finish();
            }
        });
    }
}

























