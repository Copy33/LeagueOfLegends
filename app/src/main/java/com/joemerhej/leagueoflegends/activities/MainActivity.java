package com.joemerhej.leagueoflegends.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.enums.Region;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.QueueRank;
import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;
import com.joemerhej.leagueoflegends.serverrequests.GeneralRequest;
import com.joemerhej.leagueoflegends.serverrequests.SummonerRequest;
import com.joemerhej.leagueoflegends.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainActivity extends AppCompatActivity
{
    // properties
    private Profile mProfile;

    // views
    private ImageView mProfileIcon;
    private TextView mProfileName;
    private ImageView mRankImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfile = new Profile();

        mProfileName = findViewById(R.id.mainactivity_profile_name);
        mRankImage = findViewById(R.id.mainactivity_rank_image);
        mProfileIcon = findViewById(R.id.mainactivity_profile_icon);
    }


//    void getPatchVersionsAndSummonerIcon()
//    {
//        final GeneralRequest generalRequest = new GeneralRequest(Region.EUNE);
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



































