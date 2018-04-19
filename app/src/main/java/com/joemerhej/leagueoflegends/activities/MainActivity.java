package com.joemerhej.leagueoflegends.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private ListView mWidgetListView;
    private WidgetListAdapter mWidgetListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize shared preferences
        SharedPreferencesManager.init(this);

        // set up the views
        mWidgetListView = findViewById(R.id.mainactivity_widget_list);

        // read from widget items from shared preferences and fill in the listview
        SharedPreferencesManager.readWidgetListItems(mWidgetListItems);
        mWidgetListAdapter = new WidgetListAdapter(this, mWidgetListItems);
        mWidgetListView.setAdapter(mWidgetListAdapter);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        SharedPreferencesManager.readWidgetListItems(mWidgetListItems);
        mWidgetListAdapter.notifyDataSetChanged();
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



































