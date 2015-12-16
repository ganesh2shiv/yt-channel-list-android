package app.vedicnerd.ytwua;

import android.app.Application;

import app.vedicnerd.ytwua.api.YoutubeDataClient;

public class CustomApplication extends Application {

    private static YoutubeDataClient youtubeClient;

    public static YoutubeDataClient getYoutubeClient() {
        return youtubeClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        youtubeClient = new YoutubeDataClient();
    }

}