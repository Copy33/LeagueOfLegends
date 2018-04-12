package com.joemerhej.leagueoflegends.serverrequests;

import android.util.Log;

import com.joemerhej.leagueoflegends.apis.SummonerApi;
import com.joemerhej.leagueoflegends.pojos.Summoner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class SummonerRequestEUNE
{
    private static final String TAG = "SREUNE";

    private final String mBaseUrl = "https://eun1.api.riotgames.com/";
    private Retrofit mRetrofit;
    private SummonerApi mSummonerApi;


    public interface SummonerResponseCallback
    {
        void onResponse(Summoner response, String error);
        void onFailure(Throwable t);
    }



    public SummonerRequestEUNE()
    {
        mRetrofit = RetrofitClient.getClient(mBaseUrl);
        mSummonerApi = mRetrofit.create(SummonerApi.class);
    }

    public void getSummoner(final String summonerName, final String apiKey, final SummonerResponseCallback summonerResponseCallback)
    {
        Call<Summoner> call = mSummonerApi.getSummonerInfo(summonerName, apiKey);
        call.enqueue(new Callback<Summoner>()
        {
            @Override
            public void onResponse(Call<Summoner> call, Response<Summoner> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        summonerResponseCallback.onResponse(response.body(), null);
                    }
                    else
                    {
                        String message = "Error: " + String.valueOf(response.code()) + " - " + response.message() + "\n" + response.errorBody().string();
                        summonerResponseCallback.onResponse(null, message);
                    }
                }
                catch(Exception e)
                {
                    summonerResponseCallback.onResponse(null, "Server Error");
                    Log.e(TAG, "Server Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Summoner> call, Throwable t)
            {
                summonerResponseCallback.onFailure(t);
                call.cancel();
                Log.e(TAG, "FAILURE - " + t.toString());
            }
        });
    }
}
