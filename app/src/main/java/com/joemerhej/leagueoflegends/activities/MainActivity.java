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
    private final String mApiKey = "RGAPI-6fd824ab-99ce-4d29-a835-447a1fcbf599";

    // properties
    private Profile mProfile;

    // views
    private TextView mResponse1;
    private TextView mResponse2;
    private TextView mProfileTextView;
    private ImageView mRankImage;
    private ImageView mProfileIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfile = new Profile();     //TODO: mock profile in its default constructor to show default values

        mResponse1 = findViewById(R.id.response1);
        mResponse2 = findViewById(R.id.response2);
        mProfileTextView = findViewById(R.id.profile);
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
                    mResponse1.setText(response.toString());

                    summonerRequest.getLeagueRanks(mProfile.getId().toString(), mApiKey, new SummonerRequest.SummonerResponseCallback<List<RankedData>>()
                    {
                        @Override
                        public void onResponse(List<RankedData> response, String error)
                        {
                            if(response != null && error == null)
                            {
                                mResponse2.setText(response.toString());

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

                                mProfileTextView.setText(mProfile.toString());

                                final int id = getResources().getIdentifier(mProfile.getSoloDuo().getRank().getName().toLowerCase(), "drawable", getPackageName());
                                mRankImage.setImageResource(id);

                                getPatchVersionsAndSummonerIcon();
                            }
                            else if(error != null)
                            {
                                mResponse2.setText("ERROR: " + error);
                            }
                        }

                        @Override
                        public void onFailure(Throwable t)
                        {
                            mResponse2.setText("ERROR: " + t.getLocalizedMessage());
                        }
                    });
                }
                else
                {
                    mResponse1.setText("ERROR: " + error);
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                mResponse1.setText("ERROR: " + t.getLocalizedMessage());
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
                           .into(mProfileIcon);
                }
                else
                {
                    mResponse1.setText("ERROR: " + error);
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                mResponse1.setText("ERROR: " + t.getLocalizedMessage());
            }
        });
    }
}



































