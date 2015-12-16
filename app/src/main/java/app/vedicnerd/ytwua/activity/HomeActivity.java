package app.vedicnerd.ytwua.activity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import app.vedicnerd.ytwua.CustomApplication;
import app.vedicnerd.ytwua.R;
import app.vedicnerd.ytwua.async.GetUsernameTask;
import app.vedicnerd.ytwua.fragment.VideoListFragment;
import app.vedicnerd.ytwua.fragment.YoutubePlayerFragment;
import app.vedicnerd.ytwua.pojo.PlaylistItem;
import app.vedicnerd.ytwua.pojo.PlaylistResponse;
import app.vedicnerd.ytwua.util.Constants;
import app.vedicnerd.ytwua.util.NetworkUtil;
import app.vedicnerd.ytwua.util.Utils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeActivity extends AppCompatActivity implements GetUsernameTask.InterfaceAsyncTask, VideoListFragment.OnItemSelectedListener {

    private static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    private static final int REQUEST_AUTHORIZATION = 55664;

    private SmoothProgressBar mPocketBar;

    private String nextPageToken;
    private String OAuthToken;
    private int totalResults = 0;

    private String mEmail;
    private String playlistId;

    private VideoListFragment videoListFragment;
    private YoutubePlayerFragment videoPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initView();

        if (savedInstanceState != null) {
            videoListFragment = (VideoListFragment) getSupportFragmentManager().findFragmentByTag(VideoListFragment.class.getSimpleName());
            videoPlayerFragment = (YoutubePlayerFragment) getSupportFragmentManager().findFragmentByTag(YoutubePlayerFragment.class.getSimpleName());
            switchVideoListFragment(videoListFragment, VideoListFragment.class.getSimpleName());
            switchVideoPlayerFragment(videoPlayerFragment, YoutubePlayerFragment.class.getSimpleName());
        } else {
            pickUserAccount();
            videoListFragment = VideoListFragment.newInstance();
            videoPlayerFragment = YoutubePlayerFragment.newInstance();
            switchVideoListFragment(videoListFragment, VideoListFragment.class.getSimpleName());
            switchVideoPlayerFragment(videoPlayerFragment, YoutubePlayerFragment.class.getSimpleName());
        }
    }

    private void initView() {
        mPocketBar = (SmoothProgressBar) findViewById(R.id.pocketProgressBar);
        mPocketBar.setSmoothProgressDrawableCallbacks(new SmoothProgressDrawable.Callbacks() {
            @Override
            public void onStop() {
                mPocketBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onStart() {
                mPocketBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void pickUserAccount() {
        String[] accountTypes = new String[]{"com.google"};
        Intent mIntent = AccountPicker.newChooseAccountIntent(null, null,
                accountTypes, false, null, null, null, null);
        if (mIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(mIntent, REQUEST_CODE_PICK_ACCOUNT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_ACCOUNT) {
            // Receiving a result from the AccountPicker
            if (resultCode == RESULT_OK) {
                mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                // With the account name acquired, go get the auth token
                getToken();
            }
        }

        if (requestCode == REQUEST_AUTHORIZATION) {
            if (resultCode == RESULT_OK) {
                getToken();
            }
        }
    }

    private void getToken() {
        if (mEmail == null) {
            pickUserAccount();
        } else {
            if (NetworkUtil.isNetworkAvailable(getApplicationContext())) {
                GoogleAccountCredential googleCredential = GoogleAccountCredential.usingOAuth2(getApplicationContext(), Arrays.asList(Constants.SCOPES));
                googleCredential.setBackOff(new ExponentialBackOff());
                new GetUsernameTask(this, this, googleCredential.setSelectedAccountName(mEmail)).execute();
            } else {
                Utils.showToast(getApplicationContext(), getString(R.string.internet_error_msg));
            }
        }
    }

    @Override
    public void onTokenReceived(String result) {
        if (result != null) {
            if (result.contains(" ")) {
                OAuthToken = "Bearer " + result.split(" ")[0];
                playlistId = result.split(" ")[1];
            } else {
                OAuthToken = "Bearer " + result;
            }
            Map<String, String> firstFilters = new HashMap<>();
            firstFilters.put("part", "snippet");
            firstFilters.put("maxResults", "50");
            firstFilters.put("playlistId", playlistId);

            mPocketBar.progressiveStart();

            Call<PlaylistResponse> getPlaylist = CustomApplication.getYoutubeClient().getService().getPlaylist(OAuthToken, firstFilters);
            getPlaylist.enqueue(new Callback<PlaylistResponse>() {
                @Override
                public void onResponse(Response<PlaylistResponse> playlistResponse, Retrofit retrofit) {
                    mPocketBar.progressiveStop();
                    if (playlistResponse.isSuccess() && playlistResponse.body().getPlaylistItems().size() != 0) {
                        totalResults = playlistResponse.body().getResponsePageInfo().getTotalResults();
                        nextPageToken = playlistResponse.body().getNextPageToken();

                        if (videoListFragment != null) {
                            videoListFragment.loadList(OAuthToken, playlistId, nextPageToken, playlistResponse.body().getPlaylistItems());
                        }

                        if (videoPlayerFragment != null) {
                            videoPlayerFragment.setVideoId(playlistResponse.body().getPlaylistItems().get(0).getItemSnippet().getSnippetResourceId().getVideoId());
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    mPocketBar.progressiveStop();
                    if (t instanceof IOException) {
                        Utils.showToast(getApplicationContext(), getString(R.string.internet_error_msg));
                    }
                }
            });
        }
    }

    private void switchVideoPlayerFragment(Fragment mTarget, String tag) {
        if (mTarget != null && !mTarget.isInLayout()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_player_container, mTarget, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    private void switchVideoListFragment(Fragment mTarget, String tag) {
        if (mTarget != null && !mTarget.isInLayout()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.video_list_container, mTarget, tag)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
    }

    @Override
    public void onItemSelected(PlaylistItem playlistItem) {

        if (videoPlayerFragment != null) {
            videoPlayerFragment.setVideoId(playlistItem.getItemSnippet().getSnippetResourceId().getVideoId());
        }
    }
}
