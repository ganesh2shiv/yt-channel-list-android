package app.vedicnerd.ytwua.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.vedicnerd.ytwua.CustomApplication;
import app.vedicnerd.ytwua.R;
import app.vedicnerd.ytwua.adapter.RecyclerViewFooterAdapter;
import app.vedicnerd.ytwua.listener.OnLoadMoreListener;
import app.vedicnerd.ytwua.pojo.PlaylistItem;
import app.vedicnerd.ytwua.pojo.PlaylistResponse;
import app.vedicnerd.ytwua.ui.DividerItemDecoration;
import app.vedicnerd.ytwua.util.Constants;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListFragment extends Fragment {

    private static final String PLAYLIST = "playlist";

    private View mView;
    private RecyclerView rv1;
    private RecyclerViewFooterAdapter adapter;
    private String nextPageToken;
    private String playlistId;
    private String OAuthToken;

    private ArrayList<PlaylistItem> mList;

    private Snackbar mSnackbar;

    private OnItemSelectedListener listener;

    public static VideoListFragment newInstance() {
        return new VideoListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_video_list, container, false);
        initView(savedInstanceState);
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;

            if (activity instanceof OnItemSelectedListener) {
                listener = (OnItemSelectedListener) activity;
            } else {
                throw new ClassCastException(activity.toString()
                        + " must implement OnItemSelectedListener");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PLAYLIST, mList);
    }

    private void initView(Bundle savedInstanceState) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        rv1 = (RecyclerView) mView.findViewById(R.id.recyclerView);
        rv1.setHasFixedSize(true);
        rv1.addItemDecoration(itemDecoration);
        rv1.setLayoutManager(mLayoutManager);
        rv1.setItemAnimator(new FadeInAnimator());

        if (savedInstanceState != null) {
            mList = savedInstanceState.getParcelableArrayList(PLAYLIST);
            adapter = new RecyclerViewFooterAdapter(rv1, mList, new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (OAuthToken != null) {
                        loadOauthPlaylistMore();
                    } else {
                        loadAnyPlaylistMore();
                    }
                }
            }, getContext());

            adapter.setOnItemClickListener(new RecyclerViewFooterAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(PlaylistItem playlistItem) {
                    listener.onItemSelected(playlistItem);
                }
            });

            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
            rv1.setAdapter(scaleAdapter);
        }

        mSnackbar = Snackbar.make(mView, getString(R.string.internet_error_msg), Snackbar.LENGTH_INDEFINITE);
    }

    public void loadList(final String OAuthToken, String playlistId, String nextPageToken, ArrayList<PlaylistItem> mList) {
        this.OAuthToken = OAuthToken;
        this.playlistId = playlistId;
        this.nextPageToken = nextPageToken;
        this.mList = mList;

        adapter = new RecyclerViewFooterAdapter(rv1, mList, new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (OAuthToken != null) {
                    loadOauthPlaylistMore();
                } else {
                    loadAnyPlaylistMore();
                }
            }
        }, getContext());

        adapter.setOnItemClickListener(new RecyclerViewFooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlaylistItem playlistItem) {
                listener.onItemSelected(playlistItem);
            }
        });

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        rv1.setAdapter(scaleAdapter);
    }

    private void updateAdapter(List<PlaylistItem> myDataSet, boolean shouldGoUp) {

        adapter.addItems(myDataSet);
        adapter.setLoaded();
        if (shouldGoUp) {
            if (adapter.getFirstVisibleItem() <= 50) {
                rv1.smoothScrollToPosition(0);
            } else {
                rv1.scrollToPosition(0);
            }
        }
    }

    private void loadOauthPlaylistMore() {
        adapter.addItem(null);

        Map<String, String> nextFilters = new HashMap<>();
        nextFilters.put("part", "snippet");
        nextFilters.put("maxResults", "50");
        nextFilters.put("pageToken", nextPageToken);
        nextFilters.put("playlistId", playlistId);

        Call<PlaylistResponse> getOauthPlaylistMore = CustomApplication.getYoutubeClient().getService().getOauthPlaylist(OAuthToken, nextFilters);
        getOauthPlaylistMore.enqueue(new Callback<PlaylistResponse>() {

            @Override
            public void onResponse(Response<PlaylistResponse> playlistResponse) {

                adapter.removeItem(null);
                if (playlistResponse.isSuccess() && playlistResponse.body().getPlaylistItems().size() != 0) {
                    updateAdapter(playlistResponse.body().getPlaylistItems(), false);
                    nextPageToken = playlistResponse.body().getNextPageToken();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                adapter.removeItem(null);

                if (t instanceof IOException && isAdded()) {
                    mSnackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSnackbar.dismiss();
                            loadAnyPlaylistMore();
                        }
                    });
                }
            }
        });
    }

    private void loadAnyPlaylistMore() {
        adapter.addItem(null);

        Map<String, String> nextFilters = new HashMap<>();
        nextFilters.put("part", "snippet");
        nextFilters.put("maxResults", "50");
        nextFilters.put("pageToken", nextPageToken);
        nextFilters.put("playlistId", playlistId);
        nextFilters.put("key", Constants.API_KEY);

        Call<PlaylistResponse> getAnyPlaylistMore = CustomApplication.getYoutubeClient().getService().getAnyPlaylist(nextFilters);
        getAnyPlaylistMore.enqueue(new Callback<PlaylistResponse>() {

            @Override
            public void onResponse(Response<PlaylistResponse> playlistResponse) {
                if (playlistResponse.isSuccess() && playlistResponse.body().getPlaylistItems().size() != 0) {
                    adapter.removeItem(null); // don't forget to remove the progress bar representative value
                    updateAdapter(playlistResponse.body().getPlaylistItems(), false);
                    nextPageToken = playlistResponse.body().getNextPageToken();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t instanceof IOException && isAdded()) {
                    adapter.removeItem(null);
                    mSnackbar.setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mSnackbar.dismiss();
                            loadAnyPlaylistMore();
                        }
                    });
                }
            }
        });
    }

    public interface OnItemSelectedListener {

        void onItemSelected(PlaylistItem playlistItem);

    }
}