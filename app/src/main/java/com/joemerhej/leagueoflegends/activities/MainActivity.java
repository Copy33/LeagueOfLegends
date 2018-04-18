package com.joemerhej.leagueoflegends.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.joemerhej.leagueoflegends.R;


public class MainActivity extends AppCompatActivity
{
    // properties

    // views


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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



































