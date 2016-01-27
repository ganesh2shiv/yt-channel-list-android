package app.vedicnerd.ytwua.api;

import java.util.Map;

import app.vedicnerd.ytwua.pojo.PlaylistResponse;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public class YoutubeDataClient {

    private final InterfaceYoutubeData apiService;

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
