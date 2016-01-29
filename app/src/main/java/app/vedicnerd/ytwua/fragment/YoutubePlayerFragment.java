package app.vedicnerd.ytwua.fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import app.vedicnerd.ytwua.R;
import app.vedicnerd.ytwua.util.Constants;

public class YoutubePlayerFragment extends YouTubePlayerSupportFragment implements YouTubePlayer.OnInitializedListener {

    private static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    private static final int RECOVERY_DIALOG_REQUEST = 100;
    private YouTubePlayer youtubePlayer;
    private String videoId;

    public static YoutubePlayerFragment newInstance() {
        return new YoutubePlayerFragment();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        if (bundle != null && bundle.containsKey(KEY_VIDEO_ID)) {
            videoId = bundle.getString(KEY_VIDEO_ID);
        }

        initialize(Constants.API_KEY, this);
    }

    public void setVideoId(final String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (youtubePlayer != null) {
                try {
                    youtubePlayer.loadVideo(videoId);
                } catch (IllegalStateException e) {
                    initialize(Constants.API_KEY, this);
                }
            }
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean restored) {
        youtubePlayer = player;
//      youtubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
        youtubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);
        if (videoId != null) {
            if (restored) {
                youtubePlayer.play();
            } else {
                youtubePlayer.loadVideo(videoId);
            }
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
        } else {
            if (isAdded()) {
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.error_init_failure_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (youtubePlayer != null) {
            youtubePlayer.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(KEY_VIDEO_ID, videoId);
    }

}