package com.joemerhej.leagueoflegends.serverrequests;

import android.util.Log;

import com.joemerhej.leagueoflegends.apis.GeneralApi;
import com.joemerhej.leagueoflegends.enums.Region;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class GeneralRequest
{
    private static final String TAG = "GeneralRequest";

    private String mBaseUrl = "";
    private Retrofit mRetrofit;
    private GeneralApi mGeneralApi;


    public interface GeneralResponseCallback<T>
    {
        void onResponse(T response, String error);
        void onFailure(Throwable t);
    }



    public GeneralRequest(Region region)
    {
        mBaseUrl = "https://" + region.getCode() + ".api.riotgames.com/";
        mRetrofit = RetrofitClient.getClient(mBaseUrl);
        mGeneralApi = mRetrofit.create(GeneralApi.class);
    }

    public void getPatchVersions(final String apiKey, final GeneralResponseCallback<List<String>> generalResponseCallback)
    {
        Call<List<String>> call = mGeneralApi.getPatchVersions(apiKey);
        call.enqueue(new Callback<List<String>>()
        {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response)
            {
                try
                {
                    if(response.isSuccessful())
                    {
                        generalResponseCallback.onResponse(response.body(), null);
                    }
                    else
                    {
                        String message = "Error: " + String.valueOf(response.code()) + " - " + response.message() + "\n" + response.errorBody().string();
                        generalResponseCallback.onResponse(null, message);
                    }
                }
                catch(Exception e)
                {
                    generalResponseCallback.onResponse(null, "Server Error");
                    Log.e(TAG, "Server Error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t)
            {
                generalResponseCallback.onFailure(t);
                call.cancel();
                Log.e(TAG, "FAILURE - " + t.toString());
            }
        });
    }

}



















































