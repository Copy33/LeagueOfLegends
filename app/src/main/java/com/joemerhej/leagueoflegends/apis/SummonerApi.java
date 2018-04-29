package com.joemerhej.leagueoflegends.apis;

import com.joemerhej.leagueoflegends.pojos.RankedData;
import com.joemerhej.leagueoflegends.pojos.Summoner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public interface SummonerApi
{
    @GET("summoner/{region}/{name}")
    Call<Summoner> getSummoner(@Path("region") String region,
                               @Path("name") String summonerName);

    @GET("leagues/{region}/{id}")
    Call<List<RankedData>> getLeagueRanks(@Path("region") String region,
                                          @Path("id") String summonerId);
}
