package com.joemerhej.leagueoflegends.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.adapters.WidgetListAdapter;
import com.joemerhej.leagueoflegends.models.WidgetListItem;
import com.joemerhej.leagueoflegends.sharedpreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
{
    // properties
    private List<WidgetListItem> mWidgetListItems = new ArrayList<>();

    // views
    private LinearLayout mNoWidgetsLayout;
    private LinearLayout mWidgetsLayout;
    private ListView mWidgetListView;
    private WidgetListAdapter mWidgetListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);

        // initialize shared preferences
        SharedPreferencesManager.init(this);

        // set up the views
        mNoWidgetsLayout = findViewById(R.id.mainactivity_nowidgets_layout);
        mWidgetsLayout = findViewById(R.id.mainactivity_widgets_layout);
        mWidgetListView = findViewById(R.id.mainactivity_widget_list);

        // read from widget items from shared preferences and fill in the listview
        SharedPreferencesManager.readWidgetListItems(mWidgetListItems);
        mWidgetListAdapter = new WidgetListAdapter(this, mWidgetListItems);
        mWidgetListView.setAdapter(mWidgetListAdapter);

        if(mWidgetListItems.isEmpty())
        {
            mWidgetsLayout.setVisibility(View.GONE);
            mNoWidgetsLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mWidgetsLayout.setVisibility(View.VISIBLE);
            mNoWidgetsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferencesManager.readWidgetListItems(mWidgetListItems);
        mWidgetListAdapter.notifyDataSetChanged();

        if(mWidgetListItems.isEmpty())
        {
            mWidgetsLayout.setVisibility(View.GONE);
            mNoWidgetsLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            mWidgetsLayout.setVisibility(View.VISIBLE);
            mNoWidgetsLayout.setVisibility(View.GONE);
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        // if info button pressed
        if(id == R.id.mainmenu_info_button)
        {
            Intent infoIntent = new Intent(this, InfoActivity.class);
            startActivity(infoIntent);

        }
        return super.onOptionsItemSelected(item);
    }


//    void getPatchVersionsAndSummonerIcon()
//    {
//        final GeneralRequest generalRequest = new GeneralRequest(RegionCode.EUNE);
//        generalRequest.getPatchVersions(Utils.getApiKey(), new GeneralRequest.GeneralResponseCallback<List<String>>()
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



































