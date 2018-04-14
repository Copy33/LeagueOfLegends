package com.joemerhej.leagueoflegends.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesKey;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;

/**
 * The configuration screen for the {@link Widget Widget} AppWidget.
 */
public class WidgetConfigureActivity extends Activity
{
    // constants
    private static final String PREFS_NAME = "com.joemerhej.leagueoflegends.widget";
    private static final String PREF_PREFIX_KEY = "appwidget_";

    // properties
    int mWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    // views
    EditText mWidgetText;
    Button mAddButton;


    public WidgetConfigureActivity()
    {
        super();
    }

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        // if user cancels, widget isn't created
        setResult(RESULT_CANCELED);
        setContentView(R.layout.widget_configure);

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
        mWidgetText = findViewById(R.id.appwidget_text);
        mAddButton = findViewById(R.id.add_button);

        // fill in the views
        mWidgetText.setText(SharedPreferencesManager.readWidgetString(SharedPreferencesKey.TEXT_KEY, mWidgetId));
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Context context = WidgetConfigureActivity.this;

                // When the button is clicked, store the string locally
                String widgetText = mWidgetText.getText().toString();
                SharedPreferencesManager.writeWidgetString(SharedPreferencesKey.TEXT_KEY, mWidgetId, widgetText);

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

























