package com.joemerhej.leagueoflegends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.joemerhej.leagueoflegends.enums.Region;
import com.joemerhej.leagueoflegends.pojos.Summoner;
import com.joemerhej.leagueoflegends.serverrequests.SummonerRequest;


public class MainActivity extends AppCompatActivity
{
    // Views
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.title);


        SummonerRequest summonerRequest = new SummonerRequest(Region.EUNE);
        summonerRequest.getSummoner("Mojojo", "RGAPI-f5285b2e-b9b3-4b9d-8cd5-09db3214c1d7", new SummonerRequest.SummonerResponseCallback()
        {
            @Override
            public void onResponse(Summoner response, String error)
            {
                if(response != null && error == null)
                    mTextView.setText(response.toString());
                else if(error != null)
                    mTextView.setText("ERROR: " + error);
            }

            @Override
            public void onFailure(Throwable t)
            {
                mTextView.setText("ERROR: " + t.getLocalizedMessage());
            }
        });


    }
}
