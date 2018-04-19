package com.joemerhej.leagueoflegends.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;

public class InfoActivity extends AppCompatActivity
{
    // views
    private TextView mDescription;

    // special button counter
    private static int mCounter = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // set up the toolbar and action bar back button
        Toolbar toolbar = findViewById(R.id.infoactivity_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        // get the package info
        PackageInfo pInfo = null;
        try
        {
            pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
        }
        catch(PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }

        // set up views
        mDescription = findViewById(R.id.infoactivity_description);

        String version = pInfo.versionName;
        String appName = getResources().getString(R.string.app_name);
        String description = getString(R.string.infoactivity_description, version, appName);

        mDescription.setText(description);
    }

    // click listener for special button
    public void ClearSharedPReferences(View view)
    {
        if(--mCounter <= 0)
        {
            mCounter = 10;
            SharedPreferencesManager.clearSharedPreferences();
            Toast.makeText(this, "Cleared League Badge Data, please remove your widgets and try again.", Toast.LENGTH_SHORT).show();
        }
    }
}
