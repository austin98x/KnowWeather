package com.austin.knowweather.ui.adapter;

import com.austin.knowweather.R;
import com.silencedut.baselib.commonhelper.adapter.BaseAdapterData;
import com.silencedut.weather_core.api.weatherprovider.WeatherData;

/**
 * Created by SilenceDut on 16/10/29.
 */

public class HoursForecastData implements BaseAdapterData {

    public WeatherData.HoursForecastEntity hoursForecastData;

    public HoursForecastData(WeatherData.HoursForecastEntity hoursForecastData) {
        this.hoursForecastData = hoursForecastData;
    }

    @Override
    public int getContentViewId() {
        return R.layout.weather_item_hour_forecast;
    }
}
