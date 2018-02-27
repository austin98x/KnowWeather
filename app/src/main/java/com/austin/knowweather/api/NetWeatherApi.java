package com.austin.knowweather.api;

import com.austin.knowweather.entity.AqiEntity;
import com.austin.knowweather.entity.HeWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SilenceDut on 16/10/28.
 */

public interface NetWeatherApi {

    String sHeyWeatherKey = "324d477863ac47a79ee50ec78f3f685b";

    @GET("weather")
    Call<HeWeather> getWeather(@Query("key") String key, @Query("location") String location);

    @GET("air/now")
    Call<AqiEntity> getAqi(@Query("key") String key, @Query("location") String location);
}
