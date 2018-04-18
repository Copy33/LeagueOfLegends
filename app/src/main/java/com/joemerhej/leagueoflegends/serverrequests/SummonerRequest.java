package com.joemerhej.leagueoflegends.serverrequests;

import android.util.Log;

import com.joemerhej.leagueoflegends.apis.SummonerApi;
import com.joemerhej.leagueoflegends.enums.RegionCode;
import com.joemerhej.leagueoflegends.errors.Error;
import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class SummonerRequest
{
    private static final String TAG = "SummonerRequest";

    private String mBaseUrl = "";
    private Retrofit mRetrofit;
    private SummonerApi mSummonerApi;


    public interface SummonerResponseCallback<T>
    {
        void onResponse(T response, Error error);

        void onFailure(Error error);
    }


    public SummonerRequest(RegionCode regionCode)
    {
        mBaseUrl = "https://" + regionCode.value() + ".api.riotgames.com/";
        mRetrofit = RetrofitClient.getClient(mBaseUrl);
        mSummonerApi = mRetrofit.create(SummonerApi.class);
    }

    public void getSummoner(final String summonerName, final String apiKey, final SummonerResponseCallback<Summoner> summonerResponseCallback)
    {
        Call<Summoner> call = mSummonerApi.getSummoner(summonerName, apiKey);
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
                        if(Error.from(response.code()) != Error.GENERIC_ERROR)
                        {
                            summonerResponseCallback.onResponse(null, Error.from(response.code()));
                        }
                        else
                        {
                            char firstDigit = String.valueOf(response.code()).charAt(0);

                            if(firstDigit == '4')
                            {
                                summonerResponseCallback.onResponse(null, Error.BAD_REQUEST);
                            }
                            else if(firstDigit == '5')
                            {
                                summonerResponseCallback.onResponse(null, Error.INTERNAL_SERVER_ERROR);
                            }
                            else
                            {
                                String message = "Error: " + String.valueOf(response.code()) + " - " + response.message() + "\n" + response.errorBody().string();
                                Log.e(TAG, message);
                                summonerResponseCallback.onResponse(null, Error.GENERIC_ERROR);
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                    summonerResponseCallback.onResponse(null, Error.GENERIC_ERROR);
                }
            }

            @Override
            public void onFailure(Call<Summoner> call, Throwable t)
            {
                call.cancel();
                Log.e(TAG, "FAILURE - " + t.toString());
                summonerResponseCallback.onFailure(Error.NO_INTERNET);
            }
        });
    }

    public void getLeagueRanks(final String summonerId, final String apiKey, final SummonerResponseCallback<List<RankedData>> summonerResponseCallback)
    {
        Call<List<RankedData>> call = mSummonerApi.getLeagueRanks(summonerId, apiKey);
        call.enqueue(new Callback<List<RankedData>>()
        {
            @Override
            public void onResponse(Call<List<RankedData>> call, Response<List<RankedData>> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        summonerResponseCallback.onResponse(response.body(), null);
                    }
                    else
                    {
                        if(Error.from(response.code()) != Error.GENERIC_ERROR)
                        {
                            summonerResponseCallback.onResponse(null, Error.from(response.code()));
                        }
                        else
                        {
                            char firstDigit = String.valueOf(response.code()).charAt(0);

                            if(firstDigit == '4')
                                summonerResponseCallback.onResponse(null, Error.BAD_REQUEST);
                            else if(firstDigit == '5')
                                summonerResponseCallback.onResponse(null, Error.INTERNAL_SERVER_ERROR);
                        }

                        String message = "Error: " + String.valueOf(response.code()) + " - " + response.message() + "\n" + response.errorBody().string();
                        Log.e(TAG, message);
                        summonerResponseCallback.onResponse(null, Error.GENERIC_ERROR);
                    }
                }
                catch(Exception e)
                {
                    Log.e(TAG, e.getLocalizedMessage());
                    e.printStackTrace();
                    summonerResponseCallback.onResponse(null, Error.GENERIC_ERROR);
                }
            }

            @Override
            public void onFailure(Call<List<RankedData>> call, Throwable t)
            {
                call.cancel();
                Log.e(TAG, "FAILURE - " + t.toString());
                summonerResponseCallback.onFailure(Error.NO_INTERNET);
            }
        });
    }
}



















































