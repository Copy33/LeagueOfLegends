package com.joemerhej.leagueoflegends.apis;

import com.joemerhej.leagueoflegends.pojos.Summoner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public interface SummonerApi
{
    @GET("lol/summoner/v3/summoners/by-name/{summonerName}")
    Call<Summoner> getSummonerInfo(@Path("summonerName") String summonerName,
                                   @Query("api_key") String apiKey);
}
