package com.joemerhej.leagueoflegends.serverrequests;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Joe Merhej on 4/13/18.
 */

public class RetrofitClient
{
    private static Retrofit mRetrofit = null;
    private static String mBaseUrl = "";

    static Retrofit getClient(String baseUrl)
    {
        if(mRetrofit == null || mBaseUrl.compareTo(baseUrl) != 0)
        {
            mBaseUrl = baseUrl;

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(loggingInterceptor);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .client(httpClient.build())
                    .build();
        }

        return mRetrofit;
    }
}
