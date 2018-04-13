package com.joemerhej.leagueoflegends.apis;

import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;

import java.util.List;

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
    Call<Summoner> getSummoner(@Path("summonerName") String summonerName,
                               @Query("api_key") String apiKey);

    @GET("lol/league/v3/positions/by-summoner/{summonerId}")
    Call<List<RankedData>> getLeagueRanks(@Path("summonerId") String summonerId,
                                          @Query("api_key") String apiKey);
}
