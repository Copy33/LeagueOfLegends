package com.joemerhej.leagueoflegends.apis;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public interface GeneralApi
{
    @GET("lol/static-data/v3/versions")
    Call<List<String>> getPatchVersions(@Query("api_key") String apiKey);
}
