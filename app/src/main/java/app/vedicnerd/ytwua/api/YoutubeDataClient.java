package app.vedicnerd.ytwua.api;

import java.util.Map;

import app.vedicnerd.ytwua.pojo.PlaylistResponse;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.QueryMap;

public class YoutubeDataClient {

    private InterfaceYoutubeData apiService;

    public YoutubeDataClient() {
        String BASE_URL = "https://www.googleapis.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(InterfaceYoutubeData.class);
    }

    public InterfaceYoutubeData getService() {
        return apiService;
    }

    public interface InterfaceYoutubeData {

        @Headers({"content-type: application/json"})
        @GET("/youtube/v3/playlistItems")
        Call<PlaylistResponse> getOauthPlaylist(@Header("Authorization") String OAuthToken, @QueryMap Map<String, String> query);

        @Headers({"content-type: application/json"})
        @GET("/youtube/v3/playlistItems")
        Call<PlaylistResponse> getAnyPlaylist(@QueryMap Map<String, String> query);

    }
}
