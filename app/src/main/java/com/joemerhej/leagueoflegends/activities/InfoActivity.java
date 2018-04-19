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

import com.joemerhej.leagueoflegends.R;

public class InfoActivity extends AppCompatActivity
{
    // views
    private TextView mDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


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
}
