package com.austin.knowweather;

import android.app.Application;
import android.util.Log;

import com.silencedut.weather_core.CoreManager;

import com.miui.zeus.mimo.sdk.MimoSdk;

import com.xiaomi.mistatistic.sdk.MiStatInterface;
import com.xiaomi.mistatistic.sdk.URLStatsRecorder;
import com.xiaomi.mistatistic.sdk.controller.HttpEventFilter;
import com.xiaomi.mistatistic.sdk.data.HttpEvent;

/**
 * Created by SilenceDut on 16/10/15.
 */

public class WeatherApplication extends Application {

    private static Application sApplication;

    // MiAd SDK
    private static final String APP_ID = "2882303761517722170";
    private static final String APP_KEY = "5111772279170";
    private static final String APP_TOKEN = "IMoORHJggY05k8W55kQxvA==";

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
        CoreManager.init(this);

        // MiAd SDK
        boolean isReady = MimoSdk.isSdkReady();
        MimoSdk.setEnableUpdate(false);
        MimoSdk.init(this, APP_ID, APP_KEY, APP_TOKEN);
        //MimoSdk.setDebugOn();
        //MimoSdk.setStageOn();
        Log.d("MI_AD", "mi ad sdk init finish.");

        // MiStats SDk
        MiStatInterface.initialize(this.getApplicationContext(), APP_ID, APP_KEY, "default channel");
        MiStatInterface.setUploadPolicy(MiStatInterface.UPLOAD_POLICY_WHILE_INITIALIZE, 0);
        MiStatInterface.enableLog();
        // enable exception catcher.
        MiStatInterface.enableExceptionCatcher(true);
        // enable network monitor
        URLStatsRecorder.enableAutoRecord();
        URLStatsRecorder.setEventFilter(new HttpEventFilter() {
            @Override
            public HttpEvent onEvent(HttpEvent event) {
                //Log.d("MI_STAT", event.getUrl() + " result =" + event.toJSON());
                // returns null if you want to drop this event.
                // you can modify it here too.
                return event;
            }
        });
        Log.d("MI_STAT", MiStatInterface.getDeviceID(this) + " is the device.");

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    public static Application getContext() {
        return sApplication;
    }

}
