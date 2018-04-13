package com.joemerhej.leagueoflegends.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.R;
import com.joemerhej.leagueoflegends.enums.QueueType;
import com.joemerhej.leagueoflegends.enums.Region;
import com.joemerhej.leagueoflegends.models.Profile;
import com.joemerhej.leagueoflegends.models.QueueRank;
import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;
import com.joemerhej.leagueoflegends.serverrequests.SummonerRequest;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProfile = new Profile();     //TODO: mock profile in its default constructor to show default values

        mResponse1 = findViewById(R.id.response1);
        mResponse2 = findViewById(R.id.response2);
        mProfileTextView = findViewById(R.id.profile);



        final SummonerRequest summonerRequest = new SummonerRequest(Region.EUNE);
        summonerRequest.getSummoner("Mojojo", mApiKey, new SummonerRequest.SummonerResponseCallback<Summoner>()
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
                                            mProfile.setSoloDuo(new QueueRank(QueueType.FLEX_5V5, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                            break;
                                        case FLEX_3V3:
                                            mProfile.setFlex3(new QueueRank(QueueType.FLEX_5V5, rankedData.getTier(), rankedData.getRank(), rankedData.getLeaguePoints(), rankedData.getHotStreak()));
                                            break;
                                        default:
                                            break;
                                    }
                                }

                                mProfileTextView.setText(mProfile.toString());
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
                else if(error != null)
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
