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
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainActivity extends AppCompatActivity
{
    // constants
    private final String mApiKey = "RGAPI-8183d44f-46a4-4c5a-9b9b-7beb1c323247";

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

        mProfile = new Profile();     //TODO: mock profile in its default constructor to show default values

        mProfileName = findViewById(R.id.profile_name);
        mRankImage = findViewById(R.id.rank_image);
        mProfileIcon = findViewById(R.id.profile_icon);

        getSummonerProfileAndRankedData();
    }


    void getSummonerProfileAndRankedData()
    {
        final SummonerRequest summonerRequest = new SummonerRequest(Region.EUNE);
        summonerRequest.getSummoner("mojojo", mApiKey, new SummonerRequest.SummonerResponseCallback<Summoner>()
        {
            @Override
            public void onResponse(Summoner response, String error)
            {
                if(response != null && error == null)
                {
                    mProfile.set(response.getName(), response.getId(), response.getProfileIconId(), response.getSummonerLevel());

                    summonerRequest.getLeagueRanks(mProfile.getId().toString(), mApiKey, new SummonerRequest.SummonerResponseCallback<List<RankedData>>()
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

                                // fill in the views
                                mProfileName.setText(mProfile.getName());
                                final int id = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().toLowerCase(), "drawable", getPackageName());
                                mRankImage.setImageResource(id);

                                getPatchVersionsAndSummonerIcon();
                            }
                            else if(error != null)
                            {
                                mProfileName.setText("ERROR: " + error);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t)
                        {
                            mProfileName.setText("ERROR: " + t.getLocalizedMessage());
                        }
                    });
                }
                else
                {
                    mProfileName.setText("ERROR: " + error);
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                mProfileName.setText("ERROR: " + t.getLocalizedMessage());
            }
        });
    }

    void getPatchVersionsAndSummonerIcon()
    {
        final GeneralRequest generalRequest = new GeneralRequest(Region.EUNE);
        generalRequest.getPatchVersions(mApiKey, new GeneralRequest.GeneralResponseCallback<List<String>>()
        {
            @Override
            public void onResponse(List<String> response, String error)
            {
                if(response != null && error == null)
                {
                    String currentPatch = response.get(0);
                    String iconUrl = "https://ddragon.leagueoflegends.com/cdn/" + currentPatch + "/img/profileicon/" + mProfile.getProfileIconId().toString() + ".png";

                    Picasso.get()
                           .load(iconUrl)
                           .fit()
                           .into(mProfileIcon);
                }
                else
                {
                    mProfileName.setText("ERROR: " + error);
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                mProfileName.setText("ERROR: " + t.getLocalizedMessage());
            }
        });
    }
}



































